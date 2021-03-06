package automaten;

import java.util.ArrayList;
import java.util.List;

import nutzpflanzen.Pflanze;
import nutzpflanzen.PflanzenKontrolle;

public class ErnteMaschine implements Automat
{
   private List<Pflanze> ernten(List<Pflanze> meinePflanzen)
   {
      List<Pflanze> meineErnte = new ArrayList();

      for( Pflanze pflanze : meinePflanzen )
      {
         if( PflanzenKontrolle.istErntbar(pflanze) )
         {
            meineErnte.add(pflanze);
         }
      }
      meinePflanzen.removeAll(meineErnte);
      return meinePflanzen;
   }

   @Override
   public void arbeiten(List<Pflanze> meinePflanzen)
   {
      // new Thread(() -> {
      ernten(meinePflanzen);
      // }).start();
   }

   @Override
   public void arbeiten(List<Pflanze> meinePflanzen, String pflanzenart)
   {
      // diese Methode macht nichts
   }
}
