package controller;


import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class LibrarianDashBoardFormController implements Initializable {
    public StackPane paneRoot;
    public Label lblRole;
    public Label lblTime;
    public Label lblDate;
    public Label lblUserName;
    public String userId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();
        try {
            lordForm();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDateAndTime() {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(new Date()));


        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void openAttendanceOnClicked(MouseEvent mouseEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ReaderAttendanceForm.fxml"));
        pane = fxmlLoader.load();
        ReaderAttendanceFormController controller = fxmlLoader.<ReaderAttendanceFormController>getController();
        controller.date = lblDate.getText();
        controller.time = lblTime.getText();
        paneRoot.getChildren().setAll(pane);
    }

    public void lordForm() throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/SupplyDetailForm.fxml"));
        pane = fxmlLoader.load();
        paneRoot.getChildren().setAll(pane);
    }

    public void openBookReportOnClicked(MouseEvent mouseEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/BookReportForm.fxml"));
        pane = fxmlLoader.load();
        paneRoot.getChildren().setAll(pane);
    }

    public void openManageReaderOnClicked(MouseEvent mouseEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ManageReaderForm.fxml"));
        pane = fxmlLoader.load();
        paneRoot.getChildren().setAll(pane);
    }

    public void openManageBookFormOnClicked(MouseEvent mouseEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ManageBookForm.fxml"));
        pane = fxmlLoader.load();
        paneRoot.getChildren().setAll(pane);
    }

    public void openSupplyToLibraryFormOnClicked(MouseEvent mouseEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/SupplyToLibraryForm.fxml"));
        pane = fxmlLoader.load();
        SupplyToLibraryFormController controller = fxmlLoader.<SupplyToLibraryFormController>getController();
        controller.date = lblDate.getText();
        paneRoot.getChildren().setAll(pane);
    }


    public void logOutOnClicked(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to LogOut ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/LogInForm.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage) paneRoot.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.centerOnScreen();
            stage.show();
        }
    }

    public void openSettingFormOnClicked(MouseEvent mouseEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/SettingForm.fxml"));
        pane = fxmlLoader.load();
        SettingFormController controller = fxmlLoader.<SettingFormController>getController();
        controller.userId = userId;
        controller.userRole = lblRole.getText();
        controller.userName = lblUserName.getText();
        paneRoot.getChildren().setAll(pane);

    }

    public void openBookSupplyReportOnClicked(MouseEvent mouseEvent) throws IOException {
        lordForm();
    }

    public void openManageUsersOnClicked(MouseEvent mouseEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/ManageUserForm.fxml"));
        pane = fxmlLoader.load();
        paneRoot.getChildren().setAll(pane);
    }
}
