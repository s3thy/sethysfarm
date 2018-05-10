package dao.dateien;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;

import nutzpflanzen.Pflanze;

import static dao.dateien.DateiConfig.datei;

public class SchreibeInDatei
{
   public void schreibeCsv(List<Pflanze> meinePflanzen)
   {
      StringBuilder pflanzen = null;
      int lfn = 0;

      for( Pflanze pflanze : meinePflanzen )
      {
         lfn++;
         pflanzen = new StringBuilder();

         String className = String.valueOf(pflanze.getClass());
         String[] pflanzenName = className.split(Pattern.quote("."));

         pflanzen.append(pflanzenName[1] + "_" + lfn);
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
            e.printStackTrace();
         }
      }
      System.out.println("*.csv Datei geschrieben");
   }
}

