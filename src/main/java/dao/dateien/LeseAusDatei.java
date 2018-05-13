package dao.dateien;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.Alert;
import nutzpflanzen.Mais;
import nutzpflanzen.Weizen;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;
import static dao.dateien.DateiConfig.datei;
import static funktionen.Werkzeuge.format;

/**
 * Auslesen der Farm Daten aus einer CSV Datei.<br>
 * Daten werden in zwei Listen geschrieben und ausgegeben.<br>
 *
 * @author sethy, sec@shd.de
 */
public class LeseAusDatei
{
   public int leseCsv() throws IOException
   {
      int anzahlZeilen = 0;
      BufferedReader br = null;
      try
      {
         br = new BufferedReader(new FileReader(datei));
      }
      catch(FileNotFoundException e)
      {
         System.err.println(e);
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Dateifehler");
         alert.setContentText("Datei nicht vorhanden");
         alert.showAndWait();
      }
      String currLine;

      while( (currLine = br.readLine()) != null )
      {
         String[] currLineSplit = currLine.split(";");
         int laufendeNummer = Integer.parseInt(currLineSplit[0]);
         String pflanzenArt = currLineSplit[1];
         double pflanzenHoehe = Double.parseDouble(currLineSplit[2]);

         anzahlZeilen++;
         // System.out.println(laufendeNummer + " " + pflanzenArt + " " + format(pflanzenHoehe));

         if( pflanzenArt.equals("Mais") )
         {
            maisFeld.add(new Mais(pflanzenHoehe));
         }
         if( pflanzenArt.equals("Weizen") )
         {
            weizenFeld.add(new Weizen(pflanzenHoehe));
         }
      }
      // System.out.println("\n" + anzahlZeilen + " Zeilen in CSV-Datei");
      br.close();
      return anzahlZeilen;
   }

}
