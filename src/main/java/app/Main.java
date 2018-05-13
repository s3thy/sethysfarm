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
 * <p>
 * In der Haupt-View kann man mit den obigen Buttons auswaehlen,
 * welches Feld man bearbeiten möchte.<br>
 * Zusaetzlich kann man mit den Buttons links das oben ausgewaehlte Feld
 * bearbeiten.<br>
 * Entweder jede Aktion einzeln oder es laufen, solange man AUTO gedrueckt haelt,
 * alle Aktionen der Reihe nach ab.
 * </p>
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

      // CSS wird mit der FXML geladen
      // scene.getStylesheets().add(getClass().getResource("/views/login.css").toExternalForm());

      LoginController loginController = loader.getController();
      loginController.init();
   }
}
