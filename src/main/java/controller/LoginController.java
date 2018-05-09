package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static app.Main.primaryStage;

public class LoginController
{
   @FXML
   public Button btn_login;
   @FXML
   public PasswordField txt_pwd;
   @FXML
   public TextField txt_name;

   public void init()
   {
      btn_login.setOnAction(
            this::logMeInOrNot);
   }

   private void logMeInOrNot(ActionEvent event)
   {
      String user = txt_name.getText();
      String pwd = txt_pwd.getText();

      if( user.equals("") && pwd.equals("") )
      {
         // FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/viewStats.fxml"));
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/viewStatsTabelle.fxml"));
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

         //ViewController viewController = loader.getController();
         //viewController.init();

         // Testing Table
         ViewControllerTabelle viewController = loader.getController();
         viewController.init();

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

