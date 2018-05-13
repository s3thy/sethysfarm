package nutzpflanzen;

import java.util.List;

/**
 * Methoden zum Ueberpruefen der Spezifikationen einer Pflanze oder eines Feldes.<br>
 *
 * @author sethy, sec@shd.de
 */
public class PflanzenKontrolle
{
   /**
    * Wir ueberpruefen, ob eine Pflanze erntbar ist.<br>
    *
    * @param pflanze Objekt aus einer Pflanzen-Liste
    * @return TRUE, wenn Pflanze Mindesthoehe erreicht hat
    * @author sethy, sec@shd.de
    */
   public static boolean istErntbar(Pflanze pflanze)
   {
      boolean ergebnis = false;

      if( pflanze instanceof Mais )
      {
         if( pflanze.getHoehe() < Spezifikationen.Mais.getMindestHoehe() )
         {
            return ergebnis;
         }
         else
         {
            ergebnis = true;
         }
      }
      if( pflanze instanceof Weizen )
      {
         if( pflanze.getHoehe() < Spezifikationen.Weizen.getMindestHoehe() )
         {
            return ergebnis;
         }
         else
         {
            ergebnis = true;
         }
      }
      return ergebnis;
   }

   /**
    * Wir ueberpruefen, ob ein Feld bereits die maximale Anzahl
    * an Pflanzen hat.<br>
    *
    * @param meinePflanzen eine Liste mit Pflanzen
    * @return TRUE, wenn Feld getMaximaleFeldGroesse() erreicht hat
    * @author sethy, sec@shd.de
    */
   public static boolean hatFeldMaxErreicht(List<Pflanze> meinePflanzen)
   {
      return meinePflanzen.size() >= Spezifikationen.Feld.getMaximaleFeldGroesse();
   }
}
