package controller;

import controller.login.Funktionen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController
{
   @FXML
   static public Button btn_login;
   @FXML
   static public PasswordField txt_pwd;
   @FXML
   static public TextField txt_name;

   public void init()
   {
      btn_login.setOnAction(Event -> new Funktionen().logMeInOrNot());
   }

}

