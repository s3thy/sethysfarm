package controller;

import app.ErstelleDaten;
import automaten.ErnteMaschine;
import automaten.GiessMaschine;
import automaten.SaeMaschine;
import dao.dateien.LeseAusDatei;
import dao.dateien.SchreibeInDatei;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import nutzpflanzen.Pflanze;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;
import static dao.dateien.DateiConfig.datei;

public class ViewController
{
   @FXML
   public Button btn_ernten;
   public Button btn_giessen;
   public Button btn_saeen;
   public Button btn_automat;

   public Button btn_mais;
   public Button btn_weizen;
   public Button btn_gesamt;

   public BarChart<?, ?> barchart_ertraege;

   public TextArea txt_ausgabe;
   public TextArea txt_ausgabe1;
   public TextArea txt_anzahl;
   public TextArea txt_console;

   public Button btn_opencsv;
   public Button btn_opensql;
   public Button btn_savecsv;
   public Button btn_savesql;

   public String clickedButton = "gesamt";

   public void init()
   {
      btn_ernten.setOnAction(this::erntePflanzen);
      btn_giessen.setOnAction(this::giessePflanzen);
      btn_saeen.setOnAction(this::saeePflanzen);

      btn_mais.setOnAction(this::showMaisStats);
      btn_weizen.setOnAction(this::showWeizenStats);
      btn_gesamt.setOnAction(this::showGesamtStats);

      btn_opencsv.setOnAction(this::openCsv);
      btn_opensql.setOnAction(this::openSql);
      btn_savecsv.setOnAction(this::saveCsv);
      btn_savesql.setOnAction(this::saveSql);

      if( !datei.exists() )
      {

         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("ACHTUNG");
         alert.setHeaderText("Datei nicht vorhanden");
         alert.setContentText("Pflanzen werden zufällig generiert");
         alert.showAndWait();


         new ErstelleDaten().createFarm();
         txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
         txt_ausgabe.setText(getMyData("mais"));
         txt_ausgabe1.setText(getMyData("weizen"));
         barchart_ertraege.getData().clear();
         fillBarChart("Weizen");
         fillBarChart("Mais");

      }
   }

   private void fillBarChart(String pflanzenart)
   {
      XYChart.Series set1 = new XYChart.Series();

      int lfn = 0;
      if( pflanzenart.toLowerCase().equals("mais") )
      {
         for( Pflanze pflanze : maisFeld )
         {
            lfn++;
            set1.getData().add(new XYChart.Data("Mais_" + lfn, pflanze.getHoehe()));
         }
         barchart_ertraege.getData().addAll(set1);
      }
      if( pflanzenart.toLowerCase().equals("weizen") )
      {
         for( Pflanze pflanze : weizenFeld )
         {
            lfn++;
            set1.getData().add(new XYChart.Data("Weizen_" + lfn, pflanze.getHoehe()));
         }
         barchart_ertraege.getData().addAll(set1);
      }
   }

   private String getMyData(String pflanzenart)
   {
      StringBuilder sb = new StringBuilder();
      if( pflanzenart.toLowerCase().equals("mais") )
      {
         for( Pflanze pflanze : maisFeld )
         {
            sb.append(pflanze.getClass() + ", " + pflanze.getHoehe());
            sb.append("\n");
         }
      }
      if( pflanzenart.toLowerCase().equals("weizen") )
      {
         for( Pflanze pflanze : weizenFeld )
         {
            sb.append(pflanze.getClass() + ", " + pflanze.getHoehe());
            sb.append("\n");
         }
      }
      return sb.toString();
   }

   private void showMaisStats(ActionEvent actionEvent)
   {
      clickedButton = "mais";

      // barchart_ertraege.getData().remove(0, barchart_ertraege.getData().size());
      barchart_ertraege.getData().clear();
      if( maisFeld.size() != 0 )
      {
         fillBarChart("Mais");
      }
      // TESTAUSGABE
      txt_console.setText("'mais' button clicked");
      System.out.println("'mais' button clicked");
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void showWeizenStats(ActionEvent actionEvent)
   {
      clickedButton = "weizen";

      // barchart_ertraege.getData().remove(0, barchart_ertraege.getData().size());
      barchart_ertraege.getData().clear();
      if( weizenFeld.size() != 0 )
      {
         fillBarChart("Weizen");
      }
      // TESTAUSGABE
      txt_console.setText("'weizen' button clicked");
      System.out.println("'weizen' button clicked");
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void showGesamtStats(ActionEvent actionEvent)
   {
      clickedButton = "gesamt";

      // barchart_ertraege.getData().remove(0, barchart_ertraege.getData().size());
      barchart_ertraege.getData().clear();
      if( weizenFeld.size() != 0 )
      {
         fillBarChart("Weizen");
      }
      if( maisFeld.size() != 0 )
      {
         fillBarChart("Mais");
      }

      // TESTAUSGABE
      txt_console.setText("'gesamt' button clicked");
      System.out.println("'gesamt' button clicked");
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));

   }

   private void giessePflanzen(ActionEvent event)
   {
      barchart_ertraege.getData().clear();

      switch( clickedButton )
      {
         case "mais":
            new GiessMaschine().arbeiten(maisFeld);
            fillBarChart("Mais");
            break;

         case "weizen":
            new GiessMaschine().arbeiten(weizenFeld);
            fillBarChart("Weizen");
            break;

         case "gesamt":
            new GiessMaschine().arbeiten(maisFeld);
            new GiessMaschine().arbeiten(weizenFeld);
            fillBarChart("Weizen");
            fillBarChart("Mais");
            break;
      }

      // TESTAUSGABE
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
   }

   private void saeePflanzen(ActionEvent event)
   {
      /*
      if( (maisFeld.size() == 50) || (weizenFeld.size() == 50) )
      {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("ERROR");
         alert.setHeaderText("Felder haben keinen Platz mehr");
         alert.setContentText("Du solltest so langsam mal was ernten");
         alert.showAndWait();
      }
      */

      barchart_ertraege.getData().clear();

      switch( clickedButton )
      {
         case "mais":
            new SaeMaschine().arbeiten(maisFeld, "mais");
            fillBarChart("Mais");
            break;

         case "weizen":
            new SaeMaschine().arbeiten(weizenFeld, "weizen");
            fillBarChart("Weizen");
            break;

         case "gesamt":
            new SaeMaschine().arbeiten(maisFeld, "mais");
            new SaeMaschine().arbeiten(weizenFeld, "weizen");
            fillBarChart("Weizen");
            fillBarChart("Mais");
            break;
      }

      // TESTAUSGABE
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void erntePflanzen(ActionEvent event)
   {
      barchart_ertraege.getData().clear();

      switch( clickedButton )
      {
         case "mais":
            new ErnteMaschine().arbeiten(maisFeld);
            fillBarChart("Mais");
            break;

         case "weizen":
            new ErnteMaschine().arbeiten(weizenFeld);
            fillBarChart("Weizen");
            break;

         case "gesamt":
            new ErnteMaschine().arbeiten(maisFeld);
            new ErnteMaschine().arbeiten(weizenFeld);
            fillBarChart("Weizen");
            fillBarChart("Mais");
            break;
      }

      // TESTAUSGABE
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void openCsv(ActionEvent actionEvent)
   {
      try
      {
         new LeseAusDatei().leseCsv();
      }
      catch(Exception e)
      {
         e.printStackTrace();
         txt_console.setText(e.toString());
      }
   }

   private void saveCsv(ActionEvent event)
   {
      if( maisFeld.size() > 0 )
      {
         new SchreibeInDatei().schreibeCsv(maisFeld);
         txt_console.setText("Maiskölbchen in CSV festgehalten");
         System.out.println("Maiskölbchen in CSV festgehalten");
      }
      if( weizenFeld.size() > 0 )
      {
         new SchreibeInDatei().schreibeCsv(weizenFeld);
         txt_console.setText("Weizenhalme in CSV festgehalten");
         System.out.println("Weizenhalme in CSV festgehalten");
      }
   }

   private void openSql(ActionEvent actionEvent)
   {
      // TODO
   }

   private void saveSql(ActionEvent actionEvent)
   {
      // TODO
   }

}
