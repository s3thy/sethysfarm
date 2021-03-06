package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import app.ErstelleFelder;
import automaten.Automat;
import automaten.ErnteMaschine;
import automaten.GiessMaschine;
import automaten.SaeMaschine;
import controller.view.Funktionen;
import dao.dateien.LeseAusDatei;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import nutzpflanzen.Pflanze;

import static app.ErstelleFelder.maisFeld;
import static app.ErstelleFelder.weizenFeld;
import static app.Main.primaryStage;
import static dao.dateien.DateiConfig.datei;
import static funktionen.Werkzeuge.format;

/**
 * ViewController.<br>
 * Hier weisen wir den Buttons Methoden und Funktionen zu.<br>
 * Methoden bzgl. der Buttons sind hier definiert.<br>
 *
 * @author sethy, sec@shd.de
 */
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
   public TextArea txt_ausgabeMais;
   public TextArea txt_ausgabeWeizen;
   public TextArea txt_anzahl;
   public TextArea txt_console;

   List<Automat> johnDeere = new ArrayList<Automat>();

   Timer timer = null;
   TimerTask mytask = null;

   private String clickedButton = "";

   public void init()
   {
      // jeder Button bekommt eine Methode zugewiesen
      btn_ernten.setOnAction(this::erntePflanzen);
      btn_giessen.setOnAction(this::giessePflanzen);
      btn_saeen.setOnAction(this::saeePflanzen);

      btn_automat.setOnMousePressed(this::starteAlleAutomaten);
      btn_automat.setOnMouseReleased(this::stoppeAlleAutomaten);

      btn_mais.setOnAction(this::clickedMais);
      btn_weizen.setOnAction(this::clickedWeizen);
      btn_gesamt.setOnAction(this::clickedGesamt);

      btn_opencsv.setOnAction(this::clickedOpenCsv);
      btn_savecsv.setOnAction(this::clickedSaveCsv);
      btn_opensql.setOnAction(this::clickedOpenSql);
      btn_savesql.setOnAction(this::clickedSaveSql);

      // jetzt generieren wir Daten und speichern diese in eine Datei bzw.
      // lesen die Datei aus, wenn bereits vorhanden
      if( !datei.exists() )
      {
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Info");
         alert.setHeaderText("Keine CSV-Datei vorhanden");
         alert.setContentText("Pflanzen werden nun zufällig generiert");
         alert.showAndWait();

         new ErstelleFelder().createFarm();
         System.out.println();
         System.out.println("Maisfeld hat " + maisFeld.size() + " Maiskölbchen");
         System.out.println("Weizenfeld hat " + weizenFeld.size() + " Weizendinger");
         System.out.println("Insgesamt hast du " + (weizenFeld.size() + maisFeld.size()) + " Pflanzen");
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

      johnDeere.add(new SaeMaschine());
      johnDeere.add(new GiessMaschine());
      johnDeere.add(new ErnteMaschine());

      refreshInfo();
      saveOnQuit();
   }

   private void clickedOpenSql(ActionEvent actionEvent)
   {
      new Funktionen().openSQL();
      refreshInfo();
   }

   private void clickedSaveSql(ActionEvent actionEvent)
   {
      new Funktionen().saveSQL();
   }

   // Handling beim Beenden des View Fensters
   private void saveOnQuit()
   {
      primaryStage.setOnCloseRequest(myMindowEvent -> {
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Warning");
         alert.setHeaderText("Wurden Änderungen gespeichert?");
         alert.setContentText("Bitte auswählen");

         ButtonType yesButton = new ButtonType("Ja");
         ButtonType noButton = new ButtonType("Nein");

         alert.getButtonTypes().setAll(yesButton, noButton);

         Optional<ButtonType> result = alert.showAndWait();
         if( result.get() == yesButton )
         {
            Platform.exit();
         }
         else if( result.get() == noButton )
         {
            myMindowEvent.consume();
         }
      });
   }

   private void clickedMais(ActionEvent actionEvent)
   {
      clickedButton = "mais";

      btn_mais.getStyleClass().add("clicked");
      btn_weizen.getStyleClass().remove("clicked");
      btn_gesamt.getStyleClass().remove("clicked");

      showStats();
      refreshInfo();
   }

   private void clickedWeizen(ActionEvent actionEvent)
   {
      clickedButton = "weizen";

      btn_weizen.getStyleClass().add("clicked");
      btn_mais.getStyleClass().remove("clicked");
      btn_gesamt.getStyleClass().remove("clicked");

      showStats();
      refreshInfo();
   }

   private void clickedGesamt(ActionEvent actionEvent)
   {
      clickedButton = "gesamt";

      btn_gesamt.getStyleClass().add("clicked");
      btn_mais.getStyleClass().remove("clicked");
      btn_weizen.getStyleClass().remove("clicked");

      showStats();
      refreshInfo();
   }

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
            break;
         case "weizen":
            new ErnteMaschine().arbeiten(weizenFeld);
            break;
         case "gesamt":
            new ErnteMaschine().arbeiten(maisFeld);
            new ErnteMaschine().arbeiten(weizenFeld);
            break;
      }
      refreshInfo();
   }

   private void clickedOpenCsv(ActionEvent actionEvent)
   {
      new Funktionen().openCSV();
      new Funktionen().saveCSV();
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("DAO");
      alert.setHeaderText("Lese aus CSV-Datei");
      alert.showAndWait();
      refreshInfo();
   }

   private void clickedSaveCsv(ActionEvent actionEvent)
   {
      new Funktionen().saveCSV();
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("DAO");
      alert.setHeaderText("Schreibe in CSV-Datei");
      alert.showAndWait();
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

   public void refreshInfo()
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
      txt_ausgabeMais.setText(getMyData("mais"));
      txt_ausgabeWeizen.setText(getMyData("weizen"));
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
      refreshInfo();
   }

   // Laesst alle Automaten der Reihe nach laufen
   // Btn gedrueckt halten, um Automaten in einem Loop laufen zu lassen
   private void starteAlleAutomaten(MouseEvent actionEvent)
   {
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

               for( Automat automat : johnDeere )
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
                        break;
                  }
                  txt_ausgabeMais.setText(getMyData("mais"));
                  txt_ausgabeWeizen.setText(getMyData("weizen"));
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
