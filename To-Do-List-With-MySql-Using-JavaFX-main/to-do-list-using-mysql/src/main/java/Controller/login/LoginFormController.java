package Controller.login;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class LoginFormController {


    public JFXTextField txtusername;
    public JFXTextField txtpassword;

    public void btnloginOnAction(ActionEvent actionEvent) {
        if (LoginController.getInstance().authenticateUser(txtusername.getText(),txtpassword.getText())){

            new Alert(Alert.AlertType.INFORMATION, "User Found").show();

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Please Create Account and After Login...").show();
        }
    }

    public void btnsignupOnAction(ActionEvent actionEvent) {
    }
}

