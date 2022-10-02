package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.util.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Reader;
import model.User;
import view.tm.ReaderTm;
import view.tm.UserTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class ManageUserFormController implements Initializable {


    public JFXTextField txtSearch;
    public JFXButton btnAdd;
    public TableView<UserTM> tblUser;
    public TableColumn colUserId;
    public TableColumn colName;
    public TableColumn colRole;
    public TableColumn colTp_No;
    public TableColumn colAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtAddress;
    public JFXTextField txtTp_No;
    public JFXTextField txtName;
    public JFXButton btnDelete;
    public TableColumn colEmail;
    public Label lblUserId;
    public JFXComboBox<String> cmbRole;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern emailPattern = Pattern.compile("^[A-z0-9]{3,40}[@][a-z]{2,}[.](com|lk|uk|[a-z]{2,})$");
    Pattern tp_NoPattern = Pattern.compile("^(076|077|075)(||-)[0-9]{7}$|^-$");
    Pattern AddressPattern = Pattern.compile("^[1-9 A-z\\s . ,]{3,}[A-z]{3,}(.|,)$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbRole.getItems().addAll(
                "Staff"
        );

        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colTp_No.setCellValueFactory(new PropertyValueFactory<>("tpNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            setUsersValueToTable(new UserController().getAllUser());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        storeValidations();
        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblUserId.setText(newValue.getUserId());
                txtName.setText(newValue.getUserName());
                cmbRole.setValue(newValue.getRole());
                txtTp_No.setText(newValue.getTpNo());
                txtEmail.setText(newValue.getEmail());
                txtAddress.setText(newValue.getAddress());
                btnDelete.setDisable(false);
            }
        });
        disableBtn();
        setUserId();

    }
    private void setUserId() {
        try {
            lblUserId.setText(new UserController().getUserId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void storeValidations() {
        map.put(txtName, namePattern);
        map.put(txtEmail, emailPattern);
        map.put(txtTp_No, tp_NoPattern);
        map.put(txtAddress, AddressPattern);
    }

    private void setUsersValueToTable(ArrayList<User> item) {
        ObservableList<UserTM> obList = FXCollections.observableArrayList();
        item.forEach(e -> {
            obList.add(
                    new UserTM(e.getUserId(), e.getUserName(), e.getRole(), e.getTpNo(), e.getEmail(),e.getAddress()));
        });
        tblUser.setItems(obList);
    }


    public void deleteOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to Delete " + lblUserId.getText());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new ReaderController().deleteReader(lblUserId.getText());
            setUsersValueToTable(new UserController().getAllUser());
            clearTextFieldAndDisableBtn();
        }
    }

    public void clearTextFieldAndDisableBtn() {
        cmbRole.setValue(null);
        txtName.clear();
        txtEmail.clear();
        txtTp_No.clear();
        txtAddress.clear();
        disableBtn();
    }

    public void disableBtn() {
        btnDelete.setDisable(true);
        btnAdd.setDisable(true);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }
        }
    }


    public void SearchOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        ArrayList<User> users = UserController.searchUser(txtSearch.getText());
        setUsersValueToTable(users);
    }

    public void addUserOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        User user = new User(
                lblUserId.getText(),
                txtName.getText(),
                "1234",
                cmbRole.getValue(),
                txtTp_No.getText(),
                txtEmail.getText(),
                txtAddress.getText()
        );
        if (new UserController().saveUser(user)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setUsersValueToTable(new UserController().getAllUser());
            clearTextFieldAndDisableBtn();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }
}
