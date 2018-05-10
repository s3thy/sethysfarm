package controller.view;

import dao.dateien.LeseAusDatei;
import dao.dateien.SchreibeInDatei;
import dao.datenbanken.SqlActions;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;
import static dao.dateien.DateiConfig.datei;

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
      }
   }

   public void saveCSV()
   {
      if( datei.exists() )
      {
         datei.delete();
         System.out.println("Alte Datei wurde gelöscht");
      }

      if( maisFeld.size() > 0 )
      {
         new SchreibeInDatei().schreibeCsv(maisFeld);
         System.out.println(maisFeld.size() + " Maiskölbchen in CSV festgehalten");
      }
      if( weizenFeld.size() > 0 )
      {
         new SchreibeInDatei().schreibeCsv(weizenFeld);
         System.out.println(weizenFeld.size() + " Weizenhalme in CSV festgehalten");
      }
   }

}
