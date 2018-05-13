package dao.dateien;

import java.io.File;

/**
 * Vorgaben fuer den Speicherort der Datei,
 * dessen Namen und Endung.<br>
 *
 * @author sethy, sec@shd.de
 */
public class DateiConfig
{
   static String pfad = "src/main/java/dao/";
   static String dateiname = "meinePflanzen";
   static String endung = ".csv";
   static String fullpath = pfad + dateiname + endung;

   public static File datei = new File(fullpath);
}
