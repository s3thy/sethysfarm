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
   int feldgroesse = Spezifikationen.Feld.getMaximaleFeldGroesse();
   int random;

   public List<Pflanze> createMaisFeld(List<Pflanze> maisFeld)
   {
      random = (int) (Math.random() * feldgroesse + 1);
      for( int i = 0; i < random; i++ )
      {
         double hoehe = (Math.random() * 5) + 5;
         maisFeld.add(new Mais(hoehe));
      }
      return maisFeld;
   }

   public List<Pflanze> createWeizenFeld(List<Pflanze> weizenFeld)
   {
      random = (int) (Math.random() * feldgroesse + 1);
      for( int i = 0; i < random; i++ )
      {
         double hoehe = (Math.random() * 5) + 5;
         weizenFeld.add(new Weizen(hoehe));
      }
      return weizenFeld;
   }

   public void createFarm()
   {
      createMaisFeld(maisFeld);
      createWeizenFeld(weizenFeld);

      System.out.println();
      System.out.println("Maisfeld hat " + maisFeld.size() + " Maiskölbchen");
      System.out.println("Weizenfeld hat " + weizenFeld.size() + " Weizendinger");
      System.out.println("Insgesamt hast du " + (weizenFeld.size() + maisFeld.size()) + " Pflanzen");
   }
}
