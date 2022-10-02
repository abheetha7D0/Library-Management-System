package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingFormController implements Initializable {
    public JFXButton btnCancel;
    public JFXButton btnChange;
    public JFXPasswordField txtNewPassword;
    public JFXPasswordField txtReEnteredPassword;
    public JFXPasswordField txtCurrentPassword;
    public String userId;
    public String userRole;
    public String userName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnChange.setDisable(true);
        txtCurrentPassword.clear();
        txtNewPassword.clear();
        txtReEnteredPassword.clear();

    }

    public void cancelOnClicked(MouseEvent mouseEvent) {
        validate();
    }

    public void validate() {
        txtCurrentPassword.clear();
        txtNewPassword.clear();
        txtReEnteredPassword.clear();
    }

    public void changePasswordOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        User user = new SettingController().getUser(userId, txtCurrentPassword.getText());
        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Invalid CurrentPassword..").showAndWait();

        } else if (new SettingController().updatePassword(userId, txtReEnteredPassword.getText())) {
            new Alert(Alert.AlertType.CONFIRMATION, "Changed..").show();
            btnChange.setDisable(true);
            btnCancel.setDisable(true);
            validate();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }


    public void textFieldsKeyReleased(KeyEvent keyEvent) {
        if (txtCurrentPassword.getText() == null && txtNewPassword.getText() == null && txtReEnteredPassword.getText() == null) {

        } else if (txtNewPassword.getText().equals(txtReEnteredPassword.getText())) {
            txtNewPassword.setStyle("-fx-prompt-text-fill: #000000");
            txtNewPassword.setStyle("-fx-text-fill: #000000");
            txtReEnteredPassword.setStyle("-fx-text-fill: #000000");
            txtReEnteredPassword.setStyle("-fx-prompt-text-fill: #000000");
            btnChange.setDisable(false);
        } else {
            txtNewPassword.setStyle("-fx-prompt-text-fill: #ff0000");
            txtNewPassword.setStyle("-fx-text-fill: #ff0000");
            txtReEnteredPassword.setStyle("-fx-text-fill: #ff0000");
            txtReEnteredPassword.setStyle("-fx-prompt-text-fill: #ff0000");
        }
    }
}
