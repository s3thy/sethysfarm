package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.ErstelleDaten;
import automaten.Automat;
import automaten.ErnteMaschine;
import automaten.GiessMaschine;
import automaten.SaeMaschine;
import controller.view.Funktionen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import nutzpflanzen.Pflanze;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;
import static dao.dateien.DateiConfig.datei;
import static funktionen.Werkzeuge.format;

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
   public Button btn_opencsv;
   public Button btn_opensql;
   public Button btn_savecsv;
   public Button btn_savesql;
   public BarChart<String, Double> barchart_ertraege;
   public TextArea txt_ausgabe;
   public TextArea txt_ausgabe1;
   public TextArea txt_anzahl;
   public TextArea txt_console;

   List<Automat> kitchenAid = new ArrayList<Automat>();
   Timer timer = null;
   TimerTask mytask = null;
   int zaehler = 0;
   private String clickedButton = "";

   public void init()
   {
      btn_ernten.setOnAction(this::erntePflanzen);
      btn_giessen.setOnAction(this::giessePflanzen);
      btn_saeen.setOnAction(this::saeePflanzen);
      //btn_automat.setOnMousePressed(Event -> new Funktionen().starteAlleAutomaten());
      //btn_automat.setOnMouseReleased(Event -> new Funktionen().stoppeAlleAutomaten());

      btn_automat.setOnMousePressed(this::starteAlleAutomaten);
      btn_automat.setOnMouseReleased(this::stoppeAlleAutomaten);

      btn_mais.setOnAction(this::clickedMais);
      btn_weizen.setOnAction(this::clickedWeizen);
      btn_gesamt.setOnAction(this::clickedGesamt);

      btn_opensql.setOnAction(Event -> new Funktionen().openSQL());
      btn_savesql.setOnAction(Event -> new Funktionen().saveSQL());
      btn_opencsv.setOnAction(this::clickedOpenCsv);
      btn_savecsv.setOnAction(this::clickedSaveCsv);

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

         kitchenAid.add(new SaeMaschine());
         kitchenAid.add(new GiessMaschine());
         kitchenAid.add(new ErnteMaschine());
      }
   }

   public void showMaisStats()
   {
      barchart_ertraege.getData().clear();
      if( maisFeld.size() != 0 )
      {
         fillBarChart("Mais");
      }
   }

   public void showWeizenStats()
   {
      barchart_ertraege.getData().clear();
      if( weizenFeld.size() != 0 )
      {
         fillBarChart("Weizen");
      }
   }

   public void showGesamtStats()
   {
      barchart_ertraege.getData().clear();
      if( weizenFeld.size() != 0 )
      {
         fillBarChart("Weizen");
      }
      if( maisFeld.size() != 0 )
      {
         fillBarChart("Mais");
      }
   }

   private void clickedMais(ActionEvent actionEvent)
   {
      clickedButton = "mais";

      btn_weizen.getStyleClass().remove("clicked");
      btn_gesamt.getStyleClass().remove("clicked");
      btn_mais.getStyleClass().add("clicked");

      showMaisStats();

      // TESTAUSGABE
      txt_console.setText("'mais' button clicked");
      System.out.println("'mais' button clicked");
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void clickedWeizen(ActionEvent actionEvent)
   {
      clickedButton = "weizen";

      btn_mais.getStyleClass().remove("clicked");
      btn_gesamt.getStyleClass().remove("clicked");
      btn_weizen.getStyleClass().add("clicked");

      showWeizenStats();

      // TESTAUSGABE
      txt_console.setText("'weizen' button clicked");
      System.out.println("'weizen' button clicked");
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   private void clickedGesamt(ActionEvent actionEvent)
   {
      clickedButton = "gesamt";

      btn_mais.getStyleClass().remove("clicked");
      btn_weizen.getStyleClass().remove("clicked");
      btn_gesamt.getStyleClass().add("clicked");

      showGesamtStats();

      // TESTAUSGABE
      txt_console.setText("'gesamt' button clicked");
      System.out.println("'gesamt' button clicked");
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
   }

   public void giessePflanzen(ActionEvent actionEvent)
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

   public void saeePflanzen(ActionEvent actionEvent)
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

   public void erntePflanzen(ActionEvent actionEvent)
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

   public void clickedOpenCsv(ActionEvent actionEvent)
   {
      new Funktionen().openCSV();
   }

   public void clickedSaveCsv(ActionEvent actionEvent)
   {
      new Funktionen().saveCSV();
   }

   public void fillBarChart(String pflanzenart)
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

   public String getMyData(String pflanzenart)
   {
      StringBuilder sb = new StringBuilder();
      if( pflanzenart.toLowerCase().equals("mais") )
      {
         for( Pflanze pflanze : maisFeld )
         {
            sb.append("Mais").append(", ").append(format(pflanze.getHoehe())).append("cm").append("\n");
         }
      }
      if( pflanzenart.toLowerCase().equals("weizen") )
      {
         for( Pflanze pflanze : weizenFeld )
         {
            sb.append("Weizen").append(", ").append(format(pflanze.getHoehe())).append("cm").append("\n");
         }
      }
      return sb.toString();
   }

   public void stoppeAlleAutomaten(MouseEvent actionEvent)
   {
      mytask.cancel();
      mytask = null;
      timer.cancel();
      timer = null;
   }

   public void starteAlleAutomaten(MouseEvent actionEvent)
   {
      timer = new Timer(true);
      mytask = new TimerTask()
      {
         @Override
         public void run()
         {
            {
               zaehler++;
               System.out.println(zaehler);

               String clickedPflanzenart = "";

               for( Automat automat : kitchenAid )
               {
                  System.out.println(automat.getClass() + " wird ausgeführt");

                  switch( clickedButton )
                  {
                     case "mais":
                        clickedPflanzenart = "mais";
                        if( automat instanceof SaeMaschine )
                        {
                           automat.arbeiten(maisFeld, clickedPflanzenart);
                        }
                        else
                        {
                           automat.arbeiten(maisFeld);
                        }
                        break;

                     case "weizen":
                        clickedPflanzenart = "weizen";
                        if( automat instanceof SaeMaschine )
                        {
                           automat.arbeiten(weizenFeld, clickedPflanzenart);
                        }
                        else
                        {
                           automat.arbeiten(weizenFeld);
                        }
                        break;
                     case "gesamt":
                        if( automat instanceof SaeMaschine )
                        {
                           automat.arbeiten(maisFeld, "Mais");
                           automat.arbeiten(weizenFeld, "Weizen");
                        }
                        else
                        {
                           automat.arbeiten(maisFeld);
                           automat.arbeiten(weizenFeld);
                        }
                  }

                  txt_ausgabe.setText(getMyData("mais"));
                  txt_ausgabe1.setText(getMyData("weizen"));
                  txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
               }
            }
         }
      };
      timer.scheduleAtFixedRate(mytask, 0, 500);
   }

}


