package app;

import java.util.ArrayList;
import java.util.List;

import nutzpflanzen.Mais;
import nutzpflanzen.Pflanze;
import nutzpflanzen.Spezifikationen;
import nutzpflanzen.Weizen;

/**
 * Hiermit erstellen wir zufällig Daten für unsere Felder.<br>
 * Maximale Groesse eines Feldes entspricht dem Wert, der
 * in der Enum-Klasse "Spezifikationen" hinterlegt worden ist.<br>
 * Fuer jede Getreideart wird jeweils eine Liste angelegt.<br>
 *
 * @author sethy, sec@shd.de
 */
public class ErstelleFelder
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
   }
}
