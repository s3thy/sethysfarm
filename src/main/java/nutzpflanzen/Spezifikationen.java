package nutzpflanzen;

public enum Spezifikationen
{
   Mais(100.0), Weizen(100.0), Feld(50);

   private double mindestErntHoehe;
   private int maximaleFeldGroesse;

   Spezifikationen(double hoeheDerPflanze)
   {
      this.mindestErntHoehe = hoeheDerPflanze;
   }

   Spezifikationen(int feldGroesse)
   {
      this.maximaleFeldGroesse = feldGroesse;
   }

   public double getMindestHoehe()
   {
      return mindestErntHoehe;
   }

   public int getMaximaleFeldGroesse()
   {
      return maximaleFeldGroesse;
   }
}
