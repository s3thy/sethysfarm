package app;

import automaten.ErnteMaschine;
import automaten.SaeMaschine;
import nutzpflanzen.PflanzenKontrolle;

import static app.ErstelleDaten.weizenFeld;

public class ConsoleOut
{
   public static void main(String[] args)
   {
      new ErstelleDaten().createFarm();

      /*
      weizenFeld.get(0).setHoehe(101.0);
      System.out.println(weizenFeld.get(0).getHoehe());
      System.out.println(weizenFeld.size());
      System.out.println(PflanzenKontrolle.hatFeldMaxErreicht(weizenFeld));
      new ErnteMaschine().arbeiten(weizenFeld);
      new SaeMaschine().arbeiten(weizenFeld, "wEizEn");
      System.out.println(weizenFeld.size());
      */
/*
      System.out.println();

      for( Pflanze pflanze : weizenFeld )
      {
         System.out.println("weizen hat höhe: " + pflanze.getHoehe() + ", " + istErntbar(pflanze) + ", ");
      }

      System.out.println();

      for( Pflanze pflanze : maisFeld )
      {
         System.out.println("mais hat höhe: " + pflanze.getHoehe() + ", " + istErntbar(pflanze) + ", ");
      }

      System.out.println();
*/
      /*
      System.out.println("\nWeizen Feldgroesse vor dem Ernten: " + weizenFeld.size());
      new ErnteMaschine().arbeiten(weizenFeld);
      System.out.println("\nWeizen Feldgroesse nach dem Ernten: " + weizenFeld.size());

      System.out.println("\nMais Feldgroesse vor dem Ernten: " + maisFeld.size());
      new ErnteMaschine().arbeiten(maisFeld);
      System.out.println("\nMais Feldgroesse nach dem Ernten: " + maisFeld.size());

      System.out.println("\nWeizen Feldgroesse vor dem Saeen: " + weizenFeld.size());
      new SaeMaschine().arbeiten(weizenFeld, "Mais");
      System.out.println("\nWeizen Feldgroesse nach dem Saeen: " + weizenFeld.size());

      System.out.println("\nMais Feldgroesse vor dem Saeen: " + maisFeld.size());
      new SaeMaschine().arbeiten(maisFeld, "Mais");
      System.out.println("\nMais Feldgroesse nach dem Saeen: " + maisFeld.size());
      */

   }
}
