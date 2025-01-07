package Controller.login;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void btnsignupOnAction(ActionEvent actionEvent) throws IOException {

        Stage stage =new Stage();

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/signup_form.fxml"))));
        stage.show();
    }
}

