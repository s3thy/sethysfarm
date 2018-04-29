package app;

import java.util.ArrayList;
import java.util.List;

import nutzpflanzen.Mais;
import nutzpflanzen.Pflanze;
import nutzpflanzen.Spezifikationen;

public class ErstelleDaten
{

   static public List<Pflanze> maisFeld = new ArrayList();
   static public List<Pflanze> weizenFeld = new ArrayList();

   public void createFarm()
   {
      int feldgroesse = Spezifikationen.Feld.getMaximaleFeldGroesse();
      int random;

      random = (int) (Math.random() * feldgroesse + 1);
      for( int i = 0; i < random; i++ )
      {
         double hoehe = (Math.random() * 5) + 5;
         maisFeld.add(new Mais(hoehe));
      }

      random = (int) (Math.random() * feldgroesse + 1);
      for( int i = 0; i < random; i++ )
      {
         double hoehe = (Math.random() * 5) + 5;
         weizenFeld.add(new Mais(hoehe));
      }

      System.out.println();
      System.out.println("Maisfeld hat " + maisFeld.size() + " Maiskölbchen");
      System.out.println("Weizenfeld hat " + weizenFeld.size() + " Weizendinger");
   }
}
