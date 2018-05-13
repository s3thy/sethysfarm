package automaten;

import java.util.List;

import nutzpflanzen.Mais;
import nutzpflanzen.Pflanze;
import nutzpflanzen.PflanzenKontrolle;
import nutzpflanzen.Spezifikationen;
import nutzpflanzen.Weizen;

public class SaeMaschine implements Automat
{
   private List<Pflanze> saeen(List<Pflanze> meinePflanzen, String pflanzenart)
   {
      double zufallshoehe = (Math.random() * 5.0) + 5.2;

      if( pflanzenart.toLowerCase().equals("mais") )
      {
         meinePflanzen.add(new Mais(zufallshoehe));
      }
      if( pflanzenart.toLowerCase().equals("weizen") )
      {
         meinePflanzen.add(new Weizen(zufallshoehe));
      }
      return meinePflanzen;
   }

   @Override
   public void arbeiten(List<Pflanze> meinePflanzen)
   {
      // diese Methode macht nichts
   }

   @Override
   public void arbeiten(List<Pflanze> meinePflanzen, String pflanzenart)
   {
      //new Thread(() -> {
         if( !PflanzenKontrolle.hatFeldMaxErreicht(meinePflanzen) )
         {
            int maxFeldGroesse = Spezifikationen.Feld.getMaximaleFeldGroesse();
            int aktuelleFeldGroesse = meinePflanzen.size();
            int differenz = maxFeldGroesse - aktuelleFeldGroesse;

            for( int i = 0; i < differenz; i++ )
            {
               saeen(meinePflanzen, pflanzenart);
            }
         }
      //}).start();
   }
}
