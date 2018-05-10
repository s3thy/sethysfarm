package controller.view;

import java.util.Timer;
import java.util.TimerTask;

import automaten.ErnteMaschine;
import automaten.GiessMaschine;
import automaten.SaeMaschine;
import dao.dateien.LeseAusDatei;
import dao.dateien.SchreibeInDatei;
import dao.datenbanken.SqlActions;
import javafx.scene.chart.XYChart;
import nutzpflanzen.Pflanze;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;
import static controller.ViewController.*;
import static funktionen.Werkzeuge.format;

public class Funktionen
{
   private Timer timer = null;
   private TimerTask mytask = null;
   private int zaehler = 0;

   private String clickedButton = "gesamt";

   public void stoppeAlleAutomaten()
   {
      mytask.cancel();
      mytask = null;
      timer.cancel();
      timer = null;
   }

   public void starteAlleAutomaten()
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
            }
         }
      };
      timer.scheduleAtFixedRate(mytask, 0, 500);
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
            //String kolbenName = pflanze.getClass().toString().substring(19,pflanze.getClass().toString().length());
            //sb.append(pflanze.getClass()).append(", ").append(pflanze.getHoehe());
            sb.append("Mais").append(", ").append(format(pflanze.getHoehe())).append("cm").append("\n");
         }
      }
      if( pflanzenart.toLowerCase().equals("weizen") )
      {
         for( Pflanze pflanze : weizenFeld )
         {
            //tring halmName = pflanze.getClass().toString().substring(19,pflanze.getClass().toString().length());
            sb.append("Weizen").append(", ").append(format(pflanze.getHoehe())).append("cm").append("\n");
         }
      }
      return sb.toString();
   }

   public void showMaisStats()
   {
      clickedButton = "mais";

      btn_weizen.getStyleClass().remove("clicked");
      btn_gesamt.getStyleClass().remove("clicked");
      btn_mais.getStyleClass().add("clicked");


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

   public void showWeizenStats()
   {
      clickedButton = "weizen";

      btn_mais.getStyleClass().remove("clicked");
      btn_gesamt.getStyleClass().remove("clicked");
      btn_weizen.getStyleClass().add("clicked");

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

   public void showGesamtStats()
   {
      clickedButton = "gesamt";

      btn_mais.getStyleClass().remove("clicked");
      btn_weizen.getStyleClass().remove("clicked");
      btn_gesamt.getStyleClass().add("clicked");

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

   public void giessePflanzen()
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


   public void saeePflanzen()
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

   public void erntePflanzen()
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

   public void openSQL()
   {
      new SqlActions().readSQL();
   }

   public void saveSQL()
   {
      new SqlActions().writeSQL();
   }

   public void openCSV()
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

   public void saveCSV()
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

}
