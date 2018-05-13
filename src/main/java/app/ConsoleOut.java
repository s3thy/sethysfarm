package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import automaten.Automat;
import automaten.ErnteMaschine;
import automaten.GiessMaschine;
import automaten.SaeMaschine;
import dao.dateien.LeseAusDatei;
import nutzpflanzen.Mais;
import nutzpflanzen.Pflanze;
import nutzpflanzen.Weizen;

import static app.ErstelleFelder.maisFeld;
import static app.ErstelleFelder.weizenFeld;
import static dao.dateien.DateiConfig.datei;
import static funktionen.Werkzeuge.format;

/**
 * Ein Konsolen-Programm zum Testen der Farm-Klassen und -Methoden
 * unabhaengig von der GUI.<br>
 *
 * @author sethy, sec@shd.de
 */
public class ConsoleOut
{
   public static void main(String[] args)
   {
      System.out.println("\nWillkommen auf Sethys kleiner Farm");
      System.out.println("----------------------------------\n");

      checkFile();
      farmMachines();

   }

   private static void checkFile()
   {
      // Datei mit den Feldern angelegt?
      // Wenn NEIN, Daten generieren und in csv speichern
      if( !datei.exists() )
      {
         System.out.println("Herje! Noch nichts da? Kein Problem, Du bekommst jetzt einfach mal was");
         new ErstelleFelder().createFarm();
         saveCSV();
      }
      // Wenn JA, auslesen
      else
      {
         System.out.println("Oha, da haben wir ja bereits ein paar Pflanzen. Komm' mit, ich zeige die Dir\n");
         try
         {
            new LeseAusDatei().leseCsv();

            BufferedReader br = null;
            try
            {
               br = new BufferedReader(new FileReader(datei));
            }
            catch(FileNotFoundException e)
            {
               System.err.println(e);
            }

            String currLine;
            maisFeld.clear();
            weizenFeld.clear();

            while( (currLine = br.readLine()) != null )
            {
               String[] currLineSplit = currLine.split(";");
               int laufendeNummer = Integer.parseInt(currLineSplit[0]);
               String pflanzenArt = currLineSplit[1];
               double pflanzenHoehe = Double.parseDouble(currLineSplit[2]);

               System.out.println(laufendeNummer + " " + pflanzenArt + " " + format(pflanzenHoehe));

               if( pflanzenArt.equals("Mais") )
               {
                  maisFeld.add(new Mais(pflanzenHoehe));
               }
               if( pflanzenArt.equals("Weizen") )
               {
                  weizenFeld.add(new Weizen(pflanzenHoehe));
               }
            }
            System.out.println("");
            System.out.println("Wir haben " + maisFeld.size() + " Maiskölbchen und " + weizenFeld.size() + " Weizenhalme.\n");
            br.close();
         }
         catch(IOException e)
         {
            System.err.println(e);
         }
      }
   }

   private static void farmMachines()
   {
      // Starten wir mal unsere Maschinen und fangen mit der Arbeit an.
      // In einer Liste sind anstehende Aufgaben, die abgearbeitet werden muessen.
      List<Automat> johnDeere = new ArrayList<Automat>();
      johnDeere.add(new SaeMaschine());
      johnDeere.add(new GiessMaschine());
      johnDeere.add(new ErnteMaschine());

      // Jemand muss nun endlich arbeiten
      System.out.println("Ja, jetzt wird wieder in die Hände gespuckt\n" +
                         "Wir steigern das Bruttosozialprodukt\n" +
                         "Ja, ja, ja, jetzt wird wieder in die Hände gespuckt\n");

      new Thread(() -> {
         for( Automat automat : johnDeere )
         {
            automat.arbeiten(maisFeld);
            if( automat instanceof SaeMaschine )
            {
               new SaeMaschine().arbeiten(maisFeld, "Mais");
               System.out.println("Maiskölbchen nach Arbeit: " + maisFeld.size());
            }
         }
      }).start();

      new Thread(() -> {
         for( Automat automat : johnDeere )
         {
            automat.arbeiten(weizenFeld);
            if( automat instanceof SaeMaschine )
            {
               new SaeMaschine().arbeiten(weizenFeld, "Weizen");
               System.out.println("Weizenhalme nach Arbeit: " + weizenFeld.size());
            }
         }
      }).start();
   }

   private static void saveCSV()
   {
      if( ((maisFeld.size() == 0)) && (weizenFeld.size() == 0) )
      {
         System.out.println("Leere Felder kann man nicht speichern");
      }
      if( datei.exists() && (((maisFeld.size() != 0)) || (weizenFeld.size() != 0)) )
      {
         datei.delete();
         System.out.println("Alte Datei wurde gelöscht");
      }
      if( maisFeld.size() > 0 )
      {
         schreibeCsv(maisFeld);
         System.out.println(maisFeld.size() + " Maiskölbchen in CSV festgehalten");
      }
      else
      {
         System.out.println("Maisfeld ist leer");
      }
      if( weizenFeld.size() > 0 )
      {
         schreibeCsv(weizenFeld);
         System.out.println(weizenFeld.size() + " Weizenhalme in CSV festgehalten");
      }
      else
      {
         System.out.println("Weizenfeld ist leer");
      }
   }

   private static void schreibeCsv(List<Pflanze> meinePflanzen)
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
   }
}


