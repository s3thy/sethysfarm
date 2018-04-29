package dao.datenbanken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class VerbindeMitSQL
{
   public void buildConnexion()
   {
      String url = "jdbc:mysql://localhost:3306/classicmodels";
      String user = "sethy";
      String pw = "sethy";

      try
      {
         Connection myConn = DriverManager.getConnection(url, user, pw);
         Statement myStmt = myConn.createStatement();
         ResultSet myRs = myStmt.executeQuery("select * from meinePflanzenTabelle");
         int counter = 0;
         while( myRs.next() )
         {
            counter++;
            System.out.println(counter + ". " + myRs.getString("Mais"));
         }
      }
      catch(Exception exc)
      {
         exc.printStackTrace();
      }

   }
}
