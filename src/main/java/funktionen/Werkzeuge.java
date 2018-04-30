package funktionen;

import java.text.DecimalFormat;

public class Werkzeuge
{
   public static String format(double i)
   {
      DecimalFormat f = new DecimalFormat("#0.00");
      double toFormat = ((double) Math.round(i * 100)) / 100;
      return f.format(toFormat);
   }
}
