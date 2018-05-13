package automaten;

import java.util.ArrayList;
import java.util.List;

import nutzpflanzen.Pflanze;
import nutzpflanzen.PflanzenKontrolle;

public class ErnteMaschine implements Automat
{
   private List<Pflanze> ernten(List<Pflanze> meinePflanzen)
   {
      new Thread(() -> {
      List<Pflanze> meineErnte = new ArrayList();

      for( Pflanze pflanze : meinePflanzen )
      {
         if( PflanzenKontrolle.istErntbar(pflanze) )
         {
            meineErnte.add(pflanze);
         }
      }
      meinePflanzen.removeAll(meineErnte);
      }).start();
      return meinePflanzen;
   }


   @Override
   public void arbeiten(List<Pflanze> meinePflanzen)
   {
      ernten(meinePflanzen);
   }

   @Override
   public void arbeiten(List<Pflanze> meinePflanzen, String pflanzenart)
   {
      // diese Methode macht nichts
   }
}
