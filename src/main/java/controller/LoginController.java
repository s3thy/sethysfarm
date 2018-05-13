package controller;

import controller.login.Funktionen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * LoginController.<br>
 * Ubernimmt nur die Loginfunktion.<br>
 *
 * @author sethy, sec@shd.de
 */
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
      btn_login.setOnAction(Event -> new Funktionen().logMeInOrNot(txt_pwd.getText(), txt_name.getText()));
   }

}

