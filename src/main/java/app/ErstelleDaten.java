package app;

import java.util.ArrayList;
import java.util.List;

import nutzpflanzen.Mais;
import nutzpflanzen.Pflanze;
import nutzpflanzen.Spezifikationen;
import nutzpflanzen.Weizen;

public class ErstelleDaten
{

   static public List<Pflanze> maisFeld = new ArrayList();
   static public List<Pflanze> weizenFeld = new ArrayList();

   public void createFarm()
   {
      System.out.println();
      System.out.println("---------------------------");
      System.out.println("Sethys Farm * GUI - Version");
      System.out.println("---------------------------");

      int feldgroesse = Spezifikationen.Feld.getMaximaleFeldGroesse();
      double hoehe;

      for( int i = 0; i < feldgroesse; i++ )
      {
         hoehe = (Math.random() * 5) + 5;
         maisFeld.add(new Mais(hoehe));
         hoehe = (Math.random() * 5) + 5;
         weizenFeld.add(new Weizen(hoehe));

      }
      System.out.println();
      System.out.println("Maisfeld hat " + maisFeld.size() + " Maiskölbchen");
      System.out.println("Weizenfeld hat " + weizenFeld.size() + " Weizendinger");
   }
}