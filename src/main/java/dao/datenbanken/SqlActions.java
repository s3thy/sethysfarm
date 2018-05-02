package dao.datenbanken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlActions
{
   String url = "jdbc:mysql://localhost:3306/Sethys_Farm";
   String user = "sethy";
   String pw = "sethy";

   Connection myConn = null;
   Statement myStmt = null;
   ResultSet rs = null;

   public void buildConnexion()
   {
      try
      {
         myConn = DriverManager.getConnection(url, user, pw);
         myStmt = myConn.createStatement();
      }
      catch(Exception exc)
      {
         exc.printStackTrace();
      }
   }

   public void writeSQL()
   {

      try
      {
         myConn = DriverManager.getConnection(url, user, pw);
         myStmt = myConn.createStatement();
         rs = myStmt.executeQuery(
               "CREATE SCHEMA 'Sethys_Farm' " +
               "CREATE TABLE 'Sethys_Farm'.'MeinePflanzen' ( " +
               "'idMeinePflanzen'' INT NOT NULL, " +
               "'Mais' VARCHAR(45) NULL, " +
               "'Weizen' VARCHAR(45) NULL, " +
               "PRIMARY KEY ('idMeinePflanzen')"
         );
      }

      catch(Exception exc)
      {
         exc.printStackTrace();
      }
   }

   public void readSQL()
   {
      try
      {
         myConn = DriverManager.getConnection(url, user, pw);
         myStmt = myConn.createStatement();
         rs = myStmt.executeQuery("select * from meinePflanzen");

         int counter = 0;
         while( rs.next() )
         {
            counter++;
            System.out.println(counter + ". " + rs.getString("Mais"));
         }
      }

      catch(Exception exc)
      {
         exc.printStackTrace();
      }
   }
}
