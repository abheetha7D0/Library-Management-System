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
import javafx.scene.layout.AnchorPane;
import model.Supplier;
import view.tm.SupplierTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class ManageSupplierFormController extends ValidationUtil implements Initializable {

    public AnchorPane manageContext;
    public JFXTextField txtSearch;
    public JFXButton btnAdd;
    public TableView <SupplierTm>tblSupplier;
    public TableColumn colName;
    public TableColumn colTp_No;
    public TableColumn colAddress;
    public JFXComboBox <String>cmbSupplierType;
    public TableColumn colSupplierId;
    public TableColumn colType;
    public JFXButton btnDelete;
    public JFXTextField txtName;
    public JFXTextField txtTp_No;
    public JFXTextField txtAddress;
    public JFXButton btnUpdate;
    public TableColumn colEmail;
    public JFXTextField txtEmail;
    public Label lblSupplierId;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern tp_NoPattern = Pattern.compile("^(076|077|075)(||-)[0-9]{7}$|^-$");
    Pattern AddressPattern = Pattern.compile("^[1-9 A-z\\s . ,]{3,}[A-z]{3,}(.|,)$");
    Pattern emailPattern = Pattern.compile("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$|^-$)");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbSupplierType.getItems().addAll(
                "Supplier",
                "Donator"
        );
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("SupName"));
        colType.setCellValueFactory(new PropertyValueFactory<>("supType"));
        colTp_No.setCellValueFactory(new PropertyValueFactory<>("TpNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));

        try {
            setSuppliersValueToTable(new SupplierController().getAllSupplier());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                lblSupplierId.setText(newValue.getSupplierId());
                txtName.setText(newValue.getSupName());
                cmbSupplierType.setValue(String.valueOf(newValue.getSupType()));
                txtTp_No.setText(newValue.getTpNo());
                txtAddress.setText(newValue.getAddress());
                txtEmail.setText(newValue.getEmail());
                btnDelete.setDisable(false);
                btnUpdate.setDisable(false);
            }
        });
        disableBtn();
        storeValidations();
        setBookSupplyId();
    }
    private void setBookSupplyId() {
        try {
            lblSupplierId.setText(new SupplierController().getSupplierId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void storeValidations() {
        map.put(txtName, namePattern);
        map.put(txtTp_No, tp_NoPattern);
        map.put(txtAddress, AddressPattern);;
        map.put(txtEmail, emailPattern);
    }
    public void selectedRowUpdateOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to Update " + txtName.getText()).showAndWait();
        Supplier supplier = new Supplier(
                lblSupplierId.getText(),
                txtName.getText(),
                cmbSupplierType.getValue(),
                txtTp_No.getText(),
                txtEmail.getText(),
                txtAddress.getText()
        );

        if (new SupplierController().updateSupplier(supplier)){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            setSuppliersValueToTable(new SupplierController().getAllSupplier());
        } else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnAdd);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            }
        }
        try {
            List<String> suppliers  = new SupplierController().getAllSupplierIds();
            for (String s : suppliers) {
                if (s.equals(lblSupplierId.getText())){
                    btnAdd.setDisable(true);
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to Delete " + txtName.getText());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new ReaderController().deleteReader(lblSupplierId.getText());
            setSuppliersValueToTable(new SupplierController().getAllSupplier());
            clearTextFieldAndDisableBtn();
        }
    }

    public void addSupplierOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Supplier supplier = new Supplier(lblSupplierId.getText(),txtName.getText(),cmbSupplierType.getValue(),txtTp_No.getText(),txtEmail.getText(),txtAddress.getText());
        System.out.println(supplier.toString());
        if (new SupplierController().saveSuppler(supplier)){
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setSuppliersValueToTable(new SupplierController().getAllSupplier());
            clearTextFieldAndDisableBtn();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void SearchOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<Supplier> suppliers = SupplierController.searchSupplier(txtSearch.getText());
        setSuppliersValueToTable((ArrayList<Supplier>) suppliers);
    }
    private void setSuppliersValueToTable(ArrayList<Supplier> suppliers) {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        suppliers.forEach(e->{
            obList.add(
                    new SupplierTm(e.getSupplierId(),e.getSupName(),e.getSupType(),e.getTpNo(),e.getEmail(),e.getAddress()));
        });
        tblSupplier.setItems(obList);
    }
    public void clearTextFieldAndDisableBtn(){
        txtName.clear();
        cmbSupplierType.setValue(null);
        txtTp_No.clear();
        txtAddress.clear();
        disableBtn();
    }
    public void disableBtn(){
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnAdd.setDisable(true);
    }
}
