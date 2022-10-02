package controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.AnchorPane;
import model.Reader;
import view.tm.ReaderTm;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class ManageReaderFormController extends ValidationUtil implements Initializable{

    public JFXTextField txtSearch;
    public TableView<ReaderTm> tblReader;
    public TableColumn colReaderId;
    public TableColumn colName;
    public TableColumn colAge;
    public TableColumn colTp_No;
    public TableColumn colAddress;
    public JFXTextField txtName;
    public JFXTextField txtTp_No;
    public JFXTextField txtAddress;
    public JFXTextField txtAge;
    public JFXButton btnAdd;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public AnchorPane manageContext;
    public Label lblReaderId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern agePattern = Pattern.compile("^[0-9]{2}?$");
    Pattern tp_NoPattern = Pattern.compile("^(076|077|075)(||-)[0-9]{7}$|^-$");
    Pattern AddressPattern = Pattern.compile("^[1-9 A-z\\s . ,]{3,}[A-z]{3,}(.|,)$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colReaderId.setCellValueFactory(new PropertyValueFactory<>("ReaderId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colTp_No.setCellValueFactory(new PropertyValueFactory<>("Tp_No"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));

        try {
            setReadersValueToTable(new ReaderController().getAllReader());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        storeValidations();
        tblReader.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblReaderId.setText(newValue.getReaderId());
                txtName.setText(newValue.getName());
                txtAge.setText(String.valueOf(newValue.getAge()));
                txtTp_No.setText(newValue.getTp_No());
                txtAddress.setText(newValue.getAddress());
                btnDelete.setDisable(false);
                btnUpdate.setDisable(false);
            }
        });
        disableBtn();
        setReaderId();
    }
    private void setReaderId() {
        try {
            lblReaderId.setText(new ReaderController().getReaderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void storeValidations() {
        map.put(txtName, namePattern);
        map.put(txtAge, agePattern);
        map.put(txtTp_No, tp_NoPattern);
        map.put(txtAddress, AddressPattern);
    }

    public void SearchOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<Reader> readers = ReaderController.searchReader(txtSearch.getText());
        setReadersValueToTable((ArrayList<Reader>) readers);
    }

    public void addReaderOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Reader reader = new Reader(lblReaderId.getText(), txtName.getText(), Integer.parseInt(txtAge.getText()), txtTp_No.getText(), txtAddress.getText());

        if (new ReaderController().saveReader(reader)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setReadersValueToTable(new ReaderController().getAllReader());
            clearTextFieldAndDisableBtn();
            setReaderId();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }


    public void selectedRowUpdateOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Reader reader = new Reader(
                lblReaderId.getText(),
                txtName.getText(),
                Integer.parseInt(txtAge.getText()),
                txtTp_No.getText(),
                txtAddress.getText()
        );

        if (new ReaderController().updateReader(reader)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            setReadersValueToTable(new ReaderController().getAllReader());
            clearTextFieldAndDisableBtn();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }


    private void setReadersValueToTable(ArrayList<Reader> item) {
        ObservableList<ReaderTm> obList = FXCollections.observableArrayList();
        item.forEach(e -> {
            obList.add(
                    new ReaderTm(e.getReaderId(), e.getName(), e.getAge(), e.getTp_No(), e.getAddress()));
        });
        tblReader.setItems(obList);
    }


    public void deleteOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to Delete " + lblReaderId.getText());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new ReaderController().deleteReader(lblReaderId.getText());
            setReadersValueToTable(new ReaderController().getAllReader());
            clearTextFieldAndDisableBtn();
        }
    }

    public void clearTextFieldAndDisableBtn() {

        txtName.clear();
        txtAge.clear();
        txtTp_No.clear();
        txtAddress.clear();
        disableBtn();
    }

    public void disableBtn() {
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
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
        try {
            List<String> suppliers = new ReaderController().getAllReaderIds();
            for (String s : suppliers) {
                if (s.equals(lblReaderId.getText())) {
                    btnAdd.setDisable(true);
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

}
