package app;

import controller.LoginController;
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
      Main.primaryStage = primaryStage;
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      primaryStage.setTitle("Sethys Farm");
      primaryStage.setScene(scene);
      primaryStage.setResizable(false);
      primaryStage.show();

      scene.getStylesheets().add(getClass().getResource("/views/login.css").toExternalForm());

      LoginController loginController = loader.getController();
      loginController.init();
   }
}
