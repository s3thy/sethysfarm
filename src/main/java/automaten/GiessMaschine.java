package automaten;

import java.util.List;

import nutzpflanzen.Pflanze;

import static nutzpflanzen.PflanzenKontrolle.istErntbar;

public class GiessMaschine implements Automat
{
   private List<Pflanze> giessen(List<Pflanze> meinePflanzen)
   {
      double wachstum = Math.random() * 3 + 10;

      for( Pflanze pflanze : meinePflanzen )
      {
         pflanze.setHoehe(pflanze.getHoehe() + wachstum);
      }
      return meinePflanzen;
   }

   @Override
   public void arbeiten(List<Pflanze> meinePflanzen)
   {
         giessen(meinePflanzen);

         for( Pflanze pflanze : meinePflanzen )
         {
            if( istErntbar(pflanze) )
            {
               System.out.println("Du kannst bereits Pflanzen ernten");
            }
         }
   }

   @Override
   public void arbeiten(List<Pflanze> meinePflanzen, String pflanzenart)
   {
      // diese Methode macht nichts
   }
}
