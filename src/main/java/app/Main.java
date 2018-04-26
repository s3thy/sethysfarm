package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
      this.primaryStage = primaryStage;
      Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setTitle("Sethys Farm");
      primaryStage.setScene(scene);
      primaryStage.show();

      scene.getStylesheets().add(getClass().getResource("/views/login.css").toExternalForm());
   }
}
