package funktionen;

import java.text.DecimalFormat;

/**
 * Hier befindet sich eine Sammlung von nuetzlichen Funktionen.<br>
 *
 * @author sethy, sec@shd.de
 */
public class Werkzeuge
{
   /**
    * Fuer die Anzeige von double Werten mit nur zwei Nachkommastellen.<br>
    *
    * @param i Eingabewert als double
    * @return Ausgabe double mit zwei Nachkommastellen
    * @author sethy, sec@shd.de
    */
   public static String format(double i)
   {
      DecimalFormat f = new DecimalFormat("#0.00");
      double toFormat = ((double) Math.round(i * 100)) / 100;
      return f.format(toFormat);
   }
}
