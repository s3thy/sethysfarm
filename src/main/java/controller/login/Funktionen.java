package controller.login;

import java.io.IOException;

import controller.ViewController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

import static app.Main.primaryStage;

public class Funktionen
{
   int x = 0;
   // int y = 0;

   public void twerkStage()
   {
      Timeline timelineX = new Timeline(new KeyFrame(Duration.seconds(0.05), new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent t)
         {
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
         }
      }));

      timelineX.setCycleCount(5);
      timelineX.setAutoReverse(false);
      timelineX.play();

/*
      Timeline timelineY = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent t)
         {
            if( y == 0 )
            {
               primaryStage.setY(primaryStage.getY() + 10);
               y = 1;
            }
            else
            {
               primaryStage.setY(primaryStage.getY() - 10);
               y = 0;
            }
         }
      }));

      timelineY.setCycleCount(Timeline.INDEFINITE);
      timelineY.setAutoReverse(false);
      timelineY.play();
*/
   }

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
