package controller;

import java.io.IOException;
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
import dao.dateien.LeseAusDatei;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import nutzpflanzen.Pflanze;
import nutzpflanzen.Spezifikationen;

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

   private String clickedButton = "";

   public void init()
   {
      btn_ernten.setOnAction(this::erntePflanzen);
      btn_giessen.setOnAction(this::giessePflanzen);
      btn_saeen.setOnAction(this::saeePflanzen);

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
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Info");
         alert.setHeaderText("Keine CSV-Datei vorhanden");
         alert.setContentText("Pflanzen werden nun zufällig generiert");
         alert.showAndWait();

         new ErstelleDaten().createFarm();
         new Funktionen().saveCSV();
      }
      else
      {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("Info");
         alert.setHeaderText("CSV-Datei gefunden");
         alert.setContentText("Pflanzen werden aus Datei eingelesen");
         alert.showAndWait();

         try
         {
            maisFeld.clear();
            weizenFeld.clear();
            new LeseAusDatei().leseCsv();
            refreshInfo();
         }
         catch(IOException e)
         {
            System.err.println(e);
         }
      }

      clickedButton = "gesamt";
      btn_gesamt.getStyleClass().add("clicked");

      kitchenAid.add(new SaeMaschine());
      kitchenAid.add(new GiessMaschine());
      kitchenAid.add(new ErnteMaschine());

      refreshInfo();
   }

   private void clickedMais(ActionEvent actionEvent)
   {
      clickedButton = "mais";

      btn_mais.getStyleClass().add("clicked");
      btn_weizen.getStyleClass().remove("clicked");
      btn_gesamt.getStyleClass().remove("clicked");

      showStats();
      // showMaisStats();
      refreshInfo();
   }

   private void clickedWeizen(ActionEvent actionEvent)
   {
      clickedButton = "weizen";

      btn_weizen.getStyleClass().add("clicked");
      btn_mais.getStyleClass().remove("clicked");
      btn_gesamt.getStyleClass().remove("clicked");

      showStats();
      // showWeizenStats();
      refreshInfo();
   }

   private void clickedGesamt(ActionEvent actionEvent)
   {
      clickedButton = "gesamt";

      btn_gesamt.getStyleClass().add("clicked");
      btn_mais.getStyleClass().remove("clicked");
      btn_weizen.getStyleClass().remove("clicked");

      showStats();
      //showGesamtStats();
      refreshInfo();
   }

   private void showStats()
   {
      switch( clickedButton )
      {
         case "mais":
            if( maisFeld.size() != 0 )
            {
               fillBarChart("Mais");
            }
            break;
         case "weizen":
            if( weizenFeld.size() != 0 )
            {
               fillBarChart("Weizen");
            }
            break;
         case "gesamt":
            if( weizenFeld.size() != 0 )
            {
               fillBarChart("Weizen");
            }
            if( maisFeld.size() != 0 )
            {
               fillBarChart("Mais");
            }
            break;
      }
   }

   /*
      private void showMaisStats()
      {
         if( maisFeld.size() != 0 )
         {
            fillBarChart("Mais");
         }
      }

      private void showWeizenStats()
      {
         if( weizenFeld.size() != 0 )
         {
            fillBarChart("Weizen");
         }
      }

      private void showGesamtStats()
      {
         if( weizenFeld.size() != 0 )
         {
            fillBarChart("Weizen");
         }
         if( maisFeld.size() != 0 )
         {
            fillBarChart("Mais");
         }
      }
   */

   private void giessePflanzen(ActionEvent actionEvent)
   {
      switch( clickedButton )
      {
         case "mais":
            new GiessMaschine().arbeiten(maisFeld);
            break;
         case "weizen":
            new GiessMaschine().arbeiten(weizenFeld);
            break;
         case "gesamt":
            new GiessMaschine().arbeiten(maisFeld);
            new GiessMaschine().arbeiten(weizenFeld);
            break;
      }
      refreshInfo();
   }

   private void saeePflanzen(ActionEvent actionEvent)
   {

      if( (maisFeld.size() == Spezifikationen.Feld.getMaximaleFeldGroesse()) || (weizenFeld.size() == Spezifikationen.Feld
            .getMaximaleFeldGroesse()) )
      {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("ERROR");
         alert.setHeaderText(clickedButton + "-feld(er) haben keinen Platz mehr");
         alert.setContentText("Maximalgröße beträgt " + Spezifikationen.Feld.getMaximaleFeldGroesse());
         alert.showAndWait();
      }

      switch( clickedButton )
      {
         case "mais":
            new SaeMaschine().arbeiten(maisFeld, "mais");
            break;

         case "weizen":
            new SaeMaschine().arbeiten(weizenFeld, "weizen");
            break;

         case "gesamt":
            new SaeMaschine().arbeiten(maisFeld, "mais");
            new SaeMaschine().arbeiten(weizenFeld, "weizen");
            break;
      }
      refreshInfo();
   }

   private void erntePflanzen(ActionEvent actionEvent)
   {
      switch( clickedButton )
      {
         case "mais":
            new ErnteMaschine().arbeiten(maisFeld);
            //fillBarChart("Mais");
            break;

         case "weizen":
            new ErnteMaschine().arbeiten(weizenFeld);
            //fillBarChart("Weizen");
            break;

         case "gesamt":
            new ErnteMaschine().arbeiten(maisFeld);
            new ErnteMaschine().arbeiten(weizenFeld);
            //fillBarChart("Weizen");
            //fillBarChart("Mais");
            break;
      }
      refreshInfo();
   }

   private void clickedOpenCsv(ActionEvent actionEvent)
   {
      new Funktionen().openCSV();
   }

   private void clickedSaveCsv(ActionEvent actionEvent)
   {
      new Funktionen().saveCSV();
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

   private void refreshInfo()
   {
      barchart_ertraege.getData().clear();
      switch( clickedButton )
      {
         case "mais":
            fillBarChart("Mais");
            break;
         case "weizen":
            fillBarChart("Weizen");
            break;
         case "gesamt":
            fillBarChart("Mais");
            fillBarChart("Weizen");
            break;
      }
      txt_ausgabe.setText(getMyData("mais"));
      txt_ausgabe1.setText(getMyData("weizen"));
      txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: "
                         + String.valueOf(weizenFeld.size()
                                          + " gesamt: "
                                          + (maisFeld.size() + weizenFeld.size())));
   }

   private void stoppeAlleAutomaten(MouseEvent actionEvent)
   {
      mytask.cancel();
      mytask = null;
      timer.cancel();
      timer = null;
   }

   private void starteAlleAutomaten(MouseEvent actionEvent)
   {
      barchart_ertraege.getData().clear();

      timer = new Timer(true);

      mytask = new TimerTask()
      {
         int zaehler = 0;

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

                  // TODO
                  // manipulate FX Node BarChart
                  txt_ausgabe.setText(getMyData("mais"));
                  txt_ausgabe1.setText(getMyData("weizen"));
                  txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: "
                                     + String.valueOf(weizenFeld.size()
                                                      + " gesamt: "
                                                      + (maisFeld.size() + weizenFeld.size())));
               }
            }
         }
      };
      timer.scheduleAtFixedRate(mytask, 0, 500);
   }

}
