package controller.login;

import java.io.IOException;

import controller.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import static app.Main.primaryStage;

public class Funktionen
{
   public void logMeInOrNot(String txt_pwd, String txt_name)
   {
      if( txt_name.equals("") && txt_pwd.equals("") )
      {
         // View mit BarChart
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/viewStats.fxml"));
         // View mit Table
         // FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/viewStatsTabelle.fxml"));
         Parent rootParent = null;
         try
         {
            rootParent = loader.load();
         }
         catch(IOException e)
         {
            e.printStackTrace();
         }

         Scene scene = new Scene(rootParent);
         primaryStage.setScene(scene);
         primaryStage.show();

         // CSS wird mit der FXML geladen
         // scene.getStylesheets().add(getClass().getResource("/views/viewStats.css").toExternalForm());

         // View mit BarChart
         ViewController viewController = loader.getController();
         viewController.init();

         // View mit Table
         // ViewControllerTabelle viewController = loader.getController();
         // viewController.init();

      }
      else
      {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("User hat keine Berechtigung");
         alert.setHeaderText("FEHLER (code: 1337)");
         alert.setContentText("Name und/oder Passwort nicht korrekt");
         alert.showAndWait();
      }

   }
}
