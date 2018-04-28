package controller;

import automaten.ErnteMaschine;
import automaten.GiessMaschine;
import automaten.SaeMaschine;
import dao.LeseAusDatei;
import dao.SchreibeInDatei;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import nutzpflanzen.Pflanze;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;

public class ViewController
{
   @FXML
   public Button btn_ernten;
   public Button btn_giessen;
   public Button btn_saeen;

   public Button btn_mais;
   public Button btn_weizen;
   public Button btn_gesamt;

   public BarChart<?, ?> meineErtraege;
   public TextArea txt_ausgabe;
   public TextArea txt_ausgabe1;
   public TextArea txt_anzahl;
   public Button btn_opencsv;
   public Button btn_opensql;
   public Button btn_savecsv;
   public Button btn_savesql;
   @FXML
   private CategoryAxis xAxis;
   @FXML
   private NumberAxis yAxis;
   private ObservableList<String> pflanzenArten = FXCollections.observableArrayList();

   public void init()
   {
      btn_ernten.setOnAction(this::erntePflanzen);
      btn_giessen.setOnAction(this::giessePflanzen);
      btn_saeen.setOnAction(this::saeePflanzen);
      btn_mais.setOnAction(this::showMaisStats);
      btn_weizen.setOnAction(this::showWeizenStats);
      btn_gesamt.setOnAction(this::showGesamtStats);
      btn_opencsv.setOnAction(this::opencsv);
      btn_opensql.setOnAction(this::opensql);
      btn_savecsv.setOnAction(this::saveCsv);
      btn_savesql.setOnAction(this::savesql);
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
         txt_ausgabe.setText(getMyData("mais"));
         txt_ausgabe1.setText(getMyData("weizen"));
         meineErtraege.getData().addAll(set1);
      }
      if( pflanzenart.toLowerCase().equals("weizen") )
      {
         for( Pflanze pflanze : weizenFeld )
         {
            lfn++;
            set1.getData().add(new XYChart.Data("Weizen_" + lfn, pflanze.getHoehe()));
         }
         txt_ausgabe.setText(getMyData("mais"));
         txt_ausgabe1.setText(getMyData("weizen"));
         meineErtraege.getData().addAll(set1);
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

   private void showWeizenStats(ActionEvent actionEvent)
   {
      txt_ausgabe.setText("'weizen' button clicked");
      System.out.println("'weizen' button clicked");
      // meineErtraege.getData().remove(0, meineErtraege.getData().size());
      meineErtraege.getData().clear();
      if( weizenFeld.size() != 0 )
      {
         fillBarChart("Weizen");
      }
      // TESTAUSGABE
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void showMaisStats(ActionEvent actionEvent)
   {
      txt_ausgabe.setText("'mais' button clicked");
      System.out.println("'mais' button clicked");
      // meineErtraege.getData().remove(0, meineErtraege.getData().size());
      meineErtraege.getData().clear();
      if( maisFeld.size() != 0 )
      {
         fillBarChart("Mais");
      }
      // TESTAUSGABE
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void showGesamtStats(ActionEvent actionEvent)
   {
      txt_ausgabe.setText("'gesamt' button clicked");
      System.out.println("'gesamt' button clicked");
      // meineErtraege.getData().remove(0, meineErtraege.getData().size());
      meineErtraege.getData().clear();
      if( weizenFeld.size() != 0 )
      {
         fillBarChart("Weizen");
      }
      if( maisFeld.size() != 0 )
      {
         fillBarChart("Mais");
      }
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void erntePflanzen(ActionEvent event)
   {
      new ErnteMaschine().arbeiten(maisFeld);
      new ErnteMaschine().arbeiten(weizenFeld);
      // TESTAUSGABE
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void giessePflanzen(ActionEvent event)
   {
      new GiessMaschine().arbeiten(maisFeld);
      new GiessMaschine().arbeiten(weizenFeld);
      // TESTAUSGABE
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      meineErtraege.getData().clear();
      fillBarChart("Weizen");
      fillBarChart("Mais");
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

      new SaeMaschine().arbeiten(maisFeld, "mais");
      new SaeMaschine().arbeiten(weizenFeld, "weizen");

      // TESTAUSGABE
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void opencsv(ActionEvent actionEvent)
   {
      try
      {
         new LeseAusDatei().leseCsv();
      }
      catch(Exception e)
      {
         e.printStackTrace();
         txt_ausgabe.setText(e.toString());
      }
   }

   private void saveCsv(ActionEvent event)
   {
      if( maisFeld.size() > 0 )
      {
         new SchreibeInDatei().schreibeCsv(maisFeld);
         System.out.println("Maiskölbchen in CSV geschrieben");
      }
      if( weizenFeld.size() > 0 )
      {
         new SchreibeInDatei().schreibeCsv(weizenFeld);
         System.out.println("Weizenhalme in CSV geschrieben");
      }
   }

   private void opensql(ActionEvent actionEvent)
   {
      // TODO
   }

   private void savesql(ActionEvent actionEvent)
   {
      // TODO
   }

}
