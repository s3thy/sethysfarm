package dao.dateien;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import nutzpflanzen.Pflanze;

import static dao.dateien.DateiConfig.datei;

/**
 * Schreiben der Farm Daten in eine CSV Datei.<br>
 * Jeder Satz enthält eine fortlaufende Nummer, die Pflanzenart und die Hoehe.<br>
 *
 * @author sethy, sec@shd.de
 */
public class SchreibeInDatei
{
   public void schreibeCsv(List<Pflanze> meinePflanzen)
   {
      int lfn = 0;

      if( datei.exists() )
      {
         try
         {
            lfn = new LeseAusDatei().leseCsv();
         }
         catch(IOException e)
         {
            System.err.println(e);
         }
      }

      StringBuilder pflanzen = null;

      String className = "";
      String[] pflanzenName = null;

      for( Pflanze pflanze : meinePflanzen )
      {
         lfn++;
         pflanzen = new StringBuilder();
         className = String.valueOf(pflanze.getClass());
         pflanzenName = className.split(Pattern.quote("."));

         pflanzen.append(lfn);
         pflanzen.append(';');
         pflanzen.append(pflanzenName[1]);
         pflanzen.append(';');
         pflanzen.append(pflanze.getHoehe());
         pflanzen.append(';');

         try
         {
            FileWriter fw = new FileWriter(datei, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(pflanzen.toString());
            out.flush();
            out.close();
         }
         catch(Exception e)
         {
            System.err.println(e);
         }
      }
      System.out.println("*.csv Datei geschrieben");
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("DAO");
      alert.setHeaderText("Schreibe in CSV-Datei");
      alert.setContentText("Mit " + "\"" + pflanzenName[1] + "\"" + " befüllt");
      alert.showAndWait();
   }

}
