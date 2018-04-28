package nutzpflanzen;

import java.util.List;

public class PflanzenKontrolle
{
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

   public static boolean hatFeldMaxErreicht(List<Pflanze> meinePflanzen)
   {
      return meinePflanzen.size() >= Spezifikationen.Feld.getMaximaleFeldGroesse();
   }
}
