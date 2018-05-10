package dao.dateien;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.Alert;

import static dao.dateien.DateiConfig.datei;

public class LeseAusDatei
{
   public void leseCsv() throws IOException
   {
      int lfn = 0;
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
         lfn++;
         System.out.println(lfn + " " + currLine);
      }
      br.close();
   }

}
