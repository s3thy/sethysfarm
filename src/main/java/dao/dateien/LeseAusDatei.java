package dao.dateien;

import java.io.BufferedReader;
import java.io.FileReader;

import static dao.dateien.DateiConfig.datei;

public class LeseAusDatei
{
   public void leseCsv() throws Exception
   {
      BufferedReader br = new BufferedReader(new FileReader(datei));
      String currLine;

      while( (currLine = br.readLine()) != null )
      {
         System.out.println(currLine);
      }
      br.close();
   }
}
