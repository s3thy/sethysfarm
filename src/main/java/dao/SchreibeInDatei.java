package dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import nutzpflanzen.Pflanze;

import static dao.DateiConfig.datei;

public class SchreibeInDatei
{
   public void schreibeCsv(List<Pflanze> meinePflanzen)
   {
      StringBuilder pflanzen = null;

      for( Pflanze pflanze : meinePflanzen )
      {
         pflanzen = new StringBuilder();

         pflanzen.append(pflanze.getClass());
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
      System.out.println("CSV gespeichert");
   }
}

