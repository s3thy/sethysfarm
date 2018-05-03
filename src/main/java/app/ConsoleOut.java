package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConsoleOut
{
   public static void main(String[] args)
   {
      new ErstelleDaten().createFarm();

      String url = "jdbc:mysql://localhost:3306/SethysFarm";
      String user = "sethy";
      String pw = "sethy";

      Connection myConn = null;
      Statement statement = null;
      ResultSet resultSet = null;
      PreparedStatement ps = null;

      String createTable = "CREATE TABLE IF NOT EXISTS meinePflanzen (\n"
                           + "	PflanzenID integer PRIMARY KEY,\n"
                           + "	Pflanzenart text NOT NULL,\n"
                           + "	Hoehe real\n"
                           + ");";

      String insertPlants = "INSERT INTO SethysFarm.meinePflanzen (PflanzenID, Pflanzenart, Hoehe) VALUES "
                            + "(1, 'Mais', 9.54);";

      String preparedStmt = "SELECT PflanzenID FROM SethysFarm.meinePflanzen WHERE Pflanzenart=? AND Hoehe=?";

      try
      {
         myConn = DriverManager.getConnection(url, user, pw);
         statement = myConn.createStatement();
         ps = myConn.prepareStatement(preparedStmt);

         ps.setDouble(1, 6.58);
         ps.setDouble(2, 9.87);
         resultSet = ps.executeQuery();
         while( resultSet.next() )
         {
            System.out.println(resultSet.getString(1) + "\n" + resultSet.getDouble(2));
         }

         statement.execute(createTable);
         statement.execute(insertPlants);
      }
      catch(Exception exc)
      {
         exc.printStackTrace();
      }
      finally
      {
         if( myConn != null )
         {
            try
            {
               myConn.close();
            }
            catch(Exception e)
            {
            }
         }
      }

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
