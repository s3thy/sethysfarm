package controller.view;

import dao.dateien.LeseAusDatei;
import dao.dateien.SchreibeInDatei;
import dao.datenbanken.SqlActions;
import javafx.scene.control.Alert;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;
import static dao.dateien.DateiConfig.datei;

/**
 * Einige usgelagerte Methoden fuer den ViewController<br>
 *
 * @author sethy, sec@shd.de
 */
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
         maisFeld.clear();
         weizenFeld.clear();
         new LeseAusDatei().leseCsv();
      }
      catch(Exception e)
      {
         System.err.println(e);
      }
   }

   public void saveCSV()
   {
      if( ((maisFeld.size() == 0)) && (weizenFeld.size() == 0) )
      {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("DAO");
         alert.setHeaderText("Speichern nicht möglich");
         alert.setContentText("Leere Felder kann man nicht speichern");
         alert.showAndWait();
      }
      if( datei.exists() && (((maisFeld.size() != 0)) || (weizenFeld.size() != 0)) )
      {
         datei.delete();
         System.out.println("Alte Datei wurde gelöscht");
      }
      if( maisFeld.size() > 0 )
      {
         new SchreibeInDatei().schreibeCsv(maisFeld);
         System.out.println(maisFeld.size() + " Maiskölbchen in CSV festgehalten");
      }
      else
      {
         System.out.println("Maisfeld ist leer");
      }
      if( weizenFeld.size() > 0 )
      {
         new SchreibeInDatei().schreibeCsv(weizenFeld);
         System.out.println(weizenFeld.size() + " Weizenhalme in CSV festgehalten");
      }
      else
      {
         System.out.println("Weizenfeld ist leer");
      }
   }

}
