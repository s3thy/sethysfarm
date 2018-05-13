package controller.login;

import java.io.IOException;

import controller.ViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

import static app.Main.primaryStage;

/**
 * Ausgelagerte Methoden fuer den LoginController.<br>
 *
 * @author sethy, sec@shd.de
 */
public class Funktionen
{
   int x = 0;

   /**
    * twerkStage() laesst bei Eingabe falscher Logindaten
    * das Fenster als Hinweis "twerken".<br>
    *
    * @author sethy, sec@shd.de
    */
   public void twerkStage()
   {
      Timeline timelineX = new Timeline(new KeyFrame(Duration.seconds(0.05), t -> {
         if( x == 0 )
         {
            primaryStage.setX(primaryStage.getX() + 10);
            x = 1;
         }
         else
         {
            primaryStage.setX(primaryStage.getX() - 10);
            x = 0;
         }
      }));

      timelineX.setCycleCount(5);
      timelineX.setAutoReverse(false);
      timelineX.play();
   }

   /**
    * logMeInOrNot() prueft zunaechst die eingegebenen Logindaten.<br>
    * txt_pwd und txt_name kommen aus der LoginController-Klasse
    * und werden hier ueberprueft.<br>
    * Anschliessend wird bei erfolgreicher Ueberpruefung die "View" geoeffnet.<br>
    *
    * @param txt_pwd  Passwort als String
    * @param txt_name Name als String
    * @author sethy, sec@shd.de
    */
   public void logMeInOrNot(String txt_pwd, String txt_name)
   {
      if( txt_name.equals("") && txt_pwd.equals("") )
      {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/viewStats.fxml"));
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
      }
      else
      {
         /*
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("User hat keine Berechtigung");
         alert.setHeaderText("FEHLER (code: 1337)");
         alert.setContentText("Name und/oder Passwort nicht korrekt");
         alert.showAndWait();
         */
         twerkStage();
      }
   }

}
