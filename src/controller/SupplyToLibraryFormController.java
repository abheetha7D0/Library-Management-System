package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.util.ValidationUtil;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Book;
import model.BookSupply;
import model.Supplier;
import model.BookSupplyDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.SupplyToLibraryTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class SupplyToLibraryFormController extends ValidationUtil implements Initializable {
    public JFXComboBox<String> cmbSupplierId;
    public JFXComboBox<String> cmbBookId;
    public JFXTextField txtSupplyingCount;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtUnitPrice;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colAuthorId;
    public TableColumn colPublisherId;
    public TableColumn colCopies;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public Label lblTotal;
    public JFXTextField txtSupplierType;
    public Label lblSupplyId;
    public JFXTextField txtBookName;
    public JFXTextField txtCopiesWeHave;
    public JFXTextField txtAuthorId;
    public JFXTextField txtPublisherId;
    public TableView<SupplyToLibraryTm> tblSupplierSupply;
    public JFXButton btnAddToList;
    public JFXButton btnDelete;
    public JFXButton btnAddToLibrary;
    int cartSelectedRowForRemove = -1;

    String date;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern supplyingCountPattern = Pattern.compile("^[1-9][0-9]*$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colAuthorId.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        colPublisherId.setCellValueFactory(new PropertyValueFactory<>("publisherId"));
        colCopies.setCellValueFactory(new PropertyValueFactory<>("Copies"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        storeValidations();
        setBookSupplyId();
        try {
            loadSupplierIds();
            loadBookIds();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        cmbBookId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setBookData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setSupplierData(newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
        tblSupplierSupply.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
            btnDelete.setDisable(false);
        });
        btnAddToList.setDisable(true);
        btnDelete.setDisable(true);
        btnAddToLibrary.setDisable(true);

    }

    private void storeValidations() {
        map.put(txtSupplyingCount, supplyingCountPattern);
        map.put(txtUnitPrice, pricePattern);
    }

    private void setBookSupplyId() {
        try {
            lblSupplyId.setText(new SupplyController().getSupplyId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadSupplierIds() throws SQLException, ClassNotFoundException {
        List<String> suppliers = new SupplierController().getAllSupplierIds();
        cmbSupplierId.getItems().addAll(suppliers);
    }

    private void loadBookIds() throws SQLException, ClassNotFoundException {
        List<String> suppliers = new BookController().getAllBookIds();
        cmbBookId.getItems().addAll(suppliers);
    }

    private void setBookData(String bookId) throws SQLException, ClassNotFoundException {
        Book book = new BookController().getBook(bookId);
        if (book == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtBookName.setText(book.getName());
            txtCopiesWeHave.setText(String.valueOf(book.getCopies()));
            txtAuthorId.setText(book.getAuthorId());
            txtPublisherId.setText(book.getPublisherId());

        }
    }

    private void setSupplierData(String supplierId) throws SQLException, ClassNotFoundException {
        Supplier supplier = new SupplierController().getSupplier(supplierId);
        if (supplier == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtName.setText(supplier.getSupName());
            txtSupplierType.setText(String.valueOf(supplier.getSupType()));
            txtAddress.setText(supplier.getAddress());
            if (txtSupplierType.getText().equals("Donator")) {
                txtUnitPrice.setDisable(true);
            } else {
                txtUnitPrice.setDisable(false);
            }
        }
    }

    ObservableList<SupplyToLibraryTm> obList = FXCollections.observableArrayList();

    public void addToListOrUpdateOnClicked(MouseEvent mouseEvent) {
        String bookId = cmbBookId.getValue().toString();
        String bookName = txtBookName.getText();
        String authorId = txtAuthorId.getText();
        String publisherId = txtPublisherId.getText();
        int supplyingCount = Integer.parseInt(txtSupplyingCount.getText());
        double unitPrice = txtUnitPrice.isDisable() ? 0.0 : parseDouble(txtUnitPrice.getText());
        double total = supplyingCount * unitPrice;
        SupplyToLibraryTm tm = new SupplyToLibraryTm(
                bookId,
                bookName,
                authorId,
                publisherId,
                supplyingCount,
                unitPrice,
                total
        );

        int rowNumber = isExists(tm, obList);
        if (rowNumber == -1) {
            obList.add(tm);
        } else {
            SupplyToLibraryTm temp = obList.get(rowNumber);
            SupplyToLibraryTm newTm = new SupplyToLibraryTm(
                    temp.getBookId(),
                    temp.getBookName(),
                    temp.getAuthorId(),
                    temp.getPublisherId(),
                    supplyingCount +temp.getCopies() ,
                    unitPrice,
                    total + temp.getTotal()
            );
            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblSupplierSupply.setItems(obList);
        calculateCost();
        btnAddToLibrary.setDisable(false);
    }

    private int isExists(SupplyToLibraryTm tm, ObservableList<SupplyToLibraryTm> obList) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getBookId().equals(obList.get(i).getBookId())) {
                return i;
            }
        }
        return -1;
    }

    void calculateCost() {
        if (txtUnitPrice.isDisable()) {
            lblTotal.setText("Donate");
        } else {
            double ttl = 0;
            for (SupplyToLibraryTm tm : obList
            ) {
                ttl += tm.getTotal();
            }
            lblTotal.setText(ttl + " /=");
        }
    }

    public void selectedRowDeleteOnClicked(MouseEvent mouseEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblSupplierSupply.refresh();
        }
    }

    public void addToLibraryOnClicked(MouseEvent mouseEvent) {
        ArrayList<BookSupplyDetail> items = new ArrayList<>();
        double total = 0;
        for (SupplyToLibraryTm tempTm : obList
        ) {
            total += tempTm.getTotal();
            items.add(new BookSupplyDetail(tempTm.getBookId(), tempTm.getCopies(),
                    tempTm.getTotal()));
        }


        BookSupply bookSupply = new BookSupply(lblSupplyId.getText(),
                date,
                cmbSupplierId.getValue(),
                total,
                items
        );

        if (new SupplyController().addToLibrary(bookSupply)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " Do You want to Report");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                jasperReport();
            }else {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Success...");
            }

            setBookSupplyId();
        cmbSupplierId.setValue(null);
        cmbBookId.setValue(null);
        tblSupplierSupply.getItems().clear();
        txtSupplyingCount.clear();
        txtName.clear();
        txtBookName.clear();
        txtAddress.clear();
        txtSupplierType.clear();
        txtUnitPrice.clear();
        txtCopiesWeHave.clear();
        txtPublisherId.clear();
        txtAuthorId.clear();
        btnAddToList.setDisable(true);
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void TextFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddToList);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();

            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Added").showAndWait();
            }
        }
        if (txtUnitPrice.isDisable()) {
            btnAddToList.setDisable(false);
        }

    }

    public void jasperReport() {

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/BillingReport.jrxml"));
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, new JRBeanArrayDataSource(obList.toArray()));
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

}
