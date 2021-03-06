package dao.datenbanken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import javafx.scene.control.Alert;
import nutzpflanzen.Mais;
import nutzpflanzen.Pflanze;
import nutzpflanzen.Weizen;

import static app.ErstelleFelder.maisFeld;
import static app.ErstelleFelder.weizenFeld;

/**
 * Schreiben und Lesen aus einer Datenbank.<br>
 * In meinem Beispiel wird mysql als DBMS benutzt.<br>
 *
 * @author sethy, sec@shd.de
 */
public class SqlActions
{
   String dbms = "mysql";
   String url = "jdbc:" + dbms + "://localhost:3306/";
   String user = "sethy";
   String pw = "sethy";

   Connection myConn = null;
   Statement statement;
   PreparedStatement ps;
   ResultSet rs;


   /**
    * Aufbau einer Verbindung zum DBMS
    *
    * @author sethy, sec@shd.de
    */
   public void buildConnection()
   {
      try
      {
         myConn = DriverManager.getConnection(url, user, pw);
         statement = myConn.createStatement();
      }
      catch(CommunicationsException e)
      {
         System.err.println(e);
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Verbindungsfehler");
         alert.setContentText("Verbindung zum SQL Server fehlgeschlagen");
         alert.showAndWait();
      }
      catch(Exception e)
      {
         System.err.println(e);
      }

   }

   /**
    * Befuellen der Datenbank
    *
    * @author sethy, sec@shd.de
    */
   public void writeSQL()
   {
      String dropDaBase = "DROP DATABASE IF EXISTS sethysfarm;";

      String dropTable = "DROP TABLE IF EXISTS sethysfarm.meinePflanzen;";

      String createDatabase = "CREATE DATABASE IF NOT EXISTS sethysfarm;";

      String createTable = "CREATE TABLE IF NOT EXISTS sethysfarm.meinePflanzen (\n"
                           + "	PflanzenID integer PRIMARY KEY,\n"
                           + "	Pflanzenart text NOT NULL,\n"
                           + "	Hoehe real\n"
                           + ");";

      String insertPlants = "INSERT INTO sethysfarm.meinePflanzen (PflanzenID, Pflanzenart, Hoehe) VALUES (?,?,?)";

      try
      {
         buildConnection();

         statement.execute(dropDaBase);
         statement.execute(dropTable);
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
            //ps.setString(2, "Mais_" + lnfMais);
            ps.setString(2, "Mais");
            ps.setDouble(3, pflanze.getHoehe());
            int rowOfTable = ps.executeUpdate();
         }

         for( Pflanze pflanze : weizenFeld )
         {
            lfn++;
            lfnWeizen++;
            ps.setInt(1, lfn);
            //ps.setString(2, "Weizen_" + lfnWeizen);
            ps.setString(2, "Weizen");
            ps.setDouble(3, pflanze.getHoehe());
            int rowOfTable = ps.executeUpdate();
         }
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("DAO");
         alert.setHeaderText("Schreibe in SQL Tabelle");
         alert.showAndWait();
      }
      catch(Exception e1)
      {
         System.err.println(e1);
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
               System.err.println(e2);
            }
         }
      }
   }

   /**
    * Auslesen der Datenbank
    *
    * @author sethy, sec@shd.de
    */
   public void readSQL()
   {
      try
      {
         myConn = DriverManager.getConnection(url, user, pw);
         statement = myConn.createStatement();
         rs = statement.executeQuery("select * from SethysFarm.meinePflanzen");

         while( rs.next() )
         {

            String str = rs.getString("Pflanzenart");
            double h = rs.getDouble("Hoehe");
            //System.out.println(rs.getString("Pflanzenart"));

            if( str.equals("Mais") )
            {
               maisFeld.add(new Mais(h));
            }
            else
            {
               weizenFeld.add(new Weizen(h));
            }
         }
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("DAO");
         alert.setHeaderText("Lese aus SQL Tabelle");
         alert.showAndWait();
      }
      catch(CommunicationsException e)
      {
         System.err.println(e);
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Verbindungsfehler");
         alert.setContentText("Verbindung zum SQL Server fehlgeschlagen");
         alert.showAndWait();
      }
      catch(MySQLSyntaxErrorException e)
      {
         System.err.println(e);
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Datenbankfehler");
         alert.setContentText("Tabelle exisitiert nicht");
         alert.showAndWait();
      }
      catch(Exception e)
      {
         System.err.println(e);
      }
   }

}
