package controller.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import automaten.Automat;
import automaten.ErnteMaschine;
import automaten.GiessMaschine;
import automaten.SaeMaschine;
import dao.dateien.LeseAusDatei;
import dao.dateien.SchreibeInDatei;
import dao.datenbanken.SqlActions;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;

public class Funktionen
{
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
         // txt_console.setText(e.toString());
      }
   }

   public void saveCSV()
   {
      if( maisFeld.size() > 0 )
      {
         new SchreibeInDatei().schreibeCsv(maisFeld);
         // txt_console.setText("Maiskölbchen in CSV festgehalten");
         System.out.println("Maiskölbchen in CSV festgehalten");
      }
      if( weizenFeld.size() > 0 )
      {
         new SchreibeInDatei().schreibeCsv(weizenFeld);
         // txt_console.setText("Weizenhalme in CSV festgehalten");
         System.out.println("Weizenhalme in CSV festgehalten");
      }
   }

}
