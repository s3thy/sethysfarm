package controller;

import app.ErstelleDaten;
import controller.view.Funktionen;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;
import static dao.dateien.DateiConfig.datei;

public class ViewController
{
   static public BarChart<String, Double> barchart_ertraege;
   static public TextArea txt_ausgabe;
   static public TextArea txt_ausgabe1;
   static public TextArea txt_anzahl;
   static public TextArea txt_console;
   @FXML
   static public Button btn_ernten;
   static public Button btn_giessen;
   static public Button btn_saeen;
   static public Button btn_automat;

   static public Button btn_mais;
   static public Button btn_weizen;
   static public Button btn_gesamt;

   static public Button btn_opencsv;
   static public Button btn_opensql;
   static public Button btn_savecsv;
   static public Button btn_savesql;


   public void init()
   {
      btn_ernten.setOnAction(Event -> new Funktionen().erntePflanzen());
      btn_giessen.setOnAction(Event -> new Funktionen().giessePflanzen());
      btn_saeen.setOnAction(Event -> new Funktionen().saeePflanzen());
      btn_automat.setOnMousePressed(Event -> new Funktionen().starteAlleAutomaten());
      btn_automat.setOnMouseReleased(Event -> new Funktionen().stoppeAlleAutomaten());

      btn_mais.setOnAction(Event -> new Funktionen().showMaisStats());
      btn_weizen.setOnAction(Event -> new Funktionen().showWeizenStats());
      btn_gesamt.setOnAction(Event -> new Funktionen().showGesamtStats());

      btn_opencsv.setOnAction(Event -> new Funktionen().openCSV());
      btn_savecsv.setOnAction(Event -> new Funktionen().saveCSV());
      btn_opensql.setOnAction(Event -> new Funktionen().openSQL());
      btn_savesql.setOnAction(Event -> new Funktionen().saveSQL());

      if( !datei.exists() )
      {
/*
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("ACHTUNG");
         alert.setHeaderText("Datei nicht vorhanden");
         alert.setContentText("Pflanzen werden zufällig generiert");
         alert.showAndWait();
*/

         new ErstelleDaten().createFarm();
         txt_anzahl.setText("mais: " + String.valueOf(maisFeld.size()) + " weizen: " + String.valueOf(weizenFeld.size()));
         txt_ausgabe.setText(new Funktionen().getMyData("mais"));
         txt_ausgabe1.setText(new Funktionen().getMyData("weizen"));
         barchart_ertraege.getData().clear();
         new Funktionen().fillBarChart("Weizen");
         new Funktionen().fillBarChart("Mais");

      }
   }


}
