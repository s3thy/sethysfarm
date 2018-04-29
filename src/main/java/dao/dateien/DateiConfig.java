package dao.dateien;

import java.io.File;

public class DateiConfig
{
   static String pfad = "src/main/java/dao/";
   static String dateiname = "meinePflanzen";
   static String endung = ".csv";
   static String fullpath = pfad + dateiname + endung;

   static File datei = new File(fullpath);
}
