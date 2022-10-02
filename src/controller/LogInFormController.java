package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

public class LogInFormController {

    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public Label lblWrongLogIn;
    public AnchorPane logInContext;
    public JFXButton btnLogIn;

    public void LogInOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

            checkLogin();
    }

    private void checkLogin() throws SQLException, ClassNotFoundException, IOException {
        User user = new UserController().getUser(txtUserName.getText(),txtPassword.getText());
        if (txtUserName.getText()== null && txtPassword.getText()==null) {
            lblWrongLogIn.setText("Please enter your data.");
        } else if(user!=null) {
            lblWrongLogIn.setText("Success!");
            FXMLLoader loader;
            Parent parent;
            if(user.getRole().equals("Librarian")){
                loader = new FXMLLoader(this.getClass().getResource("../view/LibrarianDashBoardForm.fxml"));
                parent = loader.load();

                LibrarianDashBoardFormController controller = loader.<LibrarianDashBoardFormController>getController();
                controller.lblRole.setText(user.getRole());
                controller.lblUserName.setText(user.getUserName());

                controller.userId= (user.getUserId());
            }else{
                loader = new FXMLLoader(this.getClass().getResource("../view/StaffDashBoardForm.fxml"));
                parent = loader.load();
                StaffDashBoardFormController controller = loader.<StaffDashBoardFormController>getController();
                controller.lblRole.setText(user.getRole());
                controller.lblUserName.setText(user.getUserName());
                controller.userId= (user.getUserId());
            }


            Stage stage = (Stage) logInContext.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
            stage.show();
        }
            lblWrongLogIn.setText("Wrong username or password!");

    }


    public void moveToPassword(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void moveToSignIn(ActionEvent actionEvent) {
        try {
            checkLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
