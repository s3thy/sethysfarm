package automaten;

import java.util.List;

import nutzpflanzen.Pflanze;

/**
 * Interface fuer die Farmmaschinen.<br>
 * Die Methode "arbeiten" fuehrt die Aktion,
 * der jeweiligen Maschinen aus.<br>
 * Die "SaeMaschine"-Klasse erwartet zusaetzlich
 * einen Pflanzenart.<br>
 *
 * @author sethy, sec@shd.de
 */
public interface Automat
{
   void arbeiten(List<Pflanze> meinePflanzen);

   void arbeiten(List<Pflanze> meinePflanzen, String pflanzenart);
}
