package app;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main-Programm und GUI des Farm Projekts.<br>
 * Logindaten fuer die Farm:<br>
 * Name: ""<br>
 * PW: ""<br>
 *
 * @author sethy, sec@shd.de
 */
public class Main extends Application
{
   public static Stage primaryStage;

   public static void main(String[] args)
   {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception
   {
      Main.primaryStage = primaryStage;
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      primaryStage.setTitle("Sethys Farm");
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.show();

      /*
      primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
      {
         int anzahlZeilen;
         int feldGesamtGroesse;

         public void handle(WindowEvent we)
         {
            try
            {
               anzahlZeilen = new LeseAusDatei().leseCsv();
               feldGesamtGroesse = maisFeld.size() + weizenFeld.size();
            }
            catch(IOException e)
            {
               e.printStackTrace();
            }

            if( anzahlZeilen != feldGesamtGroesse )

            {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Warnung");
               alert.setHeaderText("OH OH!");
               alert.setContentText("Änderungen werden nicht gespeichert");
               alert.showAndWait();
            }
         }
      });
      */

      // CSS wird in der FXML geladen
      // scene.getStylesheets().add(getClass().getResource("/views/login.css").toExternalForm());

      LoginController loginController = loader.getController();
      loginController.init();
   }
}
