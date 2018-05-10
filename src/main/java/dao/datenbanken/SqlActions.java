package dao.datenbanken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.scene.control.Alert;
import nutzpflanzen.Pflanze;

import static app.ErstelleDaten.maisFeld;
import static app.ErstelleDaten.weizenFeld;

public class SqlActions
{
   String url = "jdbc:mysql://localhost:3306/";
   String user = "sethy";
   String pw = "sethy";

   Connection myConn = null;
   Statement statement;
   PreparedStatement ps;
   ResultSet rs;

   public void buildConnexion()
   {
      try
      {
         myConn = DriverManager.getConnection(url, user, pw);
         statement = myConn.createStatement();
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }

   public void writeSQL()
   {
      String dropDaBase = "DROP DATABASE IF EXISTS sethysfarm;";

      String createDatabase = "CREATE DATABASE IF NOT EXISTS sethysfarm;";

      String createTable = "CREATE TABLE IF NOT EXISTS sethysfarm.meinePflanzen (\n"
                           + "	PflanzenID integer PRIMARY KEY,\n"
                           + "	Pflanzenart text NOT NULL,\n"
                           + "	Hoehe real\n"
                           + ");";

      String insertPlants = "INSERT INTO sethysfarm.meinePflanzen (PflanzenID, Pflanzenart, Hoehe) VALUES (?,?,?)";

      try
      {
         buildConnexion();

         statement.execute(dropDaBase);
         statement.execute(createDatabase);
         statement.execute(createTable);
         ps = myConn.prepareStatement(insertPlants);

         int lfn = 0;
         int lnfMais = 0;
         int lfnWeizen = 0;

         for( Pflanze pflanze : maisFeld )
         {
            lfn++;
            lnfMais++;
            ps.setInt(1, lfn);
            ps.setString(2, "Mais_" + lnfMais);
            ps.setDouble(3, pflanze.getHoehe());
            int rowOfTable = ps.executeUpdate();
         }

         for( Pflanze pflanze : weizenFeld )
         {
            lfn++;
            lfnWeizen++;
            ps.setInt(1, lfn);
            ps.setString(2, "Weizen_" + lfnWeizen);
            ps.setDouble(3, pflanze.getHoehe());
            int rowOfTable = ps.executeUpdate();
         }
      }
      catch(Exception e1)
      {
         e1.printStackTrace();
      }
      finally
      {
         if( myConn != null )
         {
            try
            {
               myConn.close();
            }
            catch(Exception e2)
            {
            }
         }
      }
   }

   public void readSQL()
   {
      try
      {
         myConn = DriverManager.getConnection(url, user, pw);
         statement = myConn.createStatement();
         rs = statement.executeQuery("select * from SethysFarm.meinePflanzen");

         while( rs.next() )
         {

            System.out.println(rs.getString("Pflanzenart"));
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("ACHTUNG");
         alert.setHeaderText("Table 'sethysfarm.meinepflanzen' doesn't exist");
         alert.showAndWait();
      }
   }

}
