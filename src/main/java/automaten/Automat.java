package automaten;

import java.util.List;

import nutzpflanzen.Pflanze;

public interface Automat
{
   void arbeiten(List<Pflanze> meinePflanzen);

   void arbeiten(List<Pflanze> meinePflanzen, String pflanzenart);
}
