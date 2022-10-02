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
import model.Author;
import model.Book;
import model.Publisher;
import view.tm.AuthorTm;
import view.tm.BookTm;
import view.tm.PublisherTm;


import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class ManageBookFormController extends ValidationUtil implements Initializable {

    public TableView<BookTm> tblBook;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colBookAuthorId;
    public TableColumn colBookPublisherId;
    public TableColumn colCopies;
    public JFXTextField txtBookName;
    public JFXTextField txtCopies;
    public JFXComboBox cmbAuthor;
    public JFXComboBox cmbPublisher;
    public TableView<AuthorTm> tblAuthor;
    public TableColumn AuthorColId;
    public TableColumn AuthorColName;
    public TableColumn AuthorColTpNo;
    public TableColumn AuthorColEmail;
    public JFXTextField txtAuthorName;
    public JFXTextField txtAuthorTpNo;
    public JFXTextField txtAuthorEmail;
    public TableColumn publisherColId;
    public TableColumn publisherColName;
    public TableColumn publisherColTpNo;
    public TableColumn publisherColEmail;
    public TableColumn colBookUnitPrice;

    public JFXTextField txtPublisherName;
    public JFXTextField txtPublisherTpNo;
    public JFXTextField txtPublisherEmail;
    public TableView<PublisherTm> tblPublisher;
    public JFXTextField txtTblAuthorSearch;
    public JFXTextField txtTblPublisherSearch;

    public JFXButton btnBookAdd;
    public JFXButton btnBookDelete;
    public JFXButton btnBookUpdate;
    public JFXButton btnAuthorAdd;
    public JFXButton btnAuthorDelete;
    public JFXButton btnAuthorUpdate;
    public JFXButton btnPublisherAdd;
    public JFXButton btnPublisherDelete;
    public JFXButton btnPublisherUpdate;
    public Tab tabBook;
    public Tab tabAuthor;
    public Tab tabPublisher;
    public AnchorPane bookContext;
    public TableColumn publisherColAddress;
    public JFXTextField txtPublisherAddress;
    public JFXTextField txtSearchBook;
    public JFXButton btnCancel;
    public Label lblBookId;
    public Label lblAuthorId;
    public Label lblPublisherId;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> map2 = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> map3 = new LinkedHashMap();
    Pattern copiesPattern = Pattern.compile("^[1-9][0-9]*$");

    Pattern namePattern = Pattern.compile("^[[1-z, ] {2,30} [ 1-z ,]  ,]{2,30}| [ 1-z ] {2,30}$");
    Pattern publisherNamePattern = Pattern.compile("^[A-z ]{3,}|[(]|[.|) A-z ]{3,}$");
    Pattern AddressPattern = Pattern.compile("^[[1-z, ] {1,20}  [ 1-z , ]  ,]{1,}| [ 1-z, ] {1,}$");

    Pattern tp_NoPattern = Pattern.compile("^(076|077|075|011|071)(||-)[0-9]{7}$|^-$");
    Pattern emailPattern = Pattern.compile("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$|^-$)");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        storeValidations();
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colBookAuthorId.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        colBookPublisherId.setCellValueFactory(new PropertyValueFactory<>("publisherId"));
        colBookUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colCopies.setCellValueFactory(new PropertyValueFactory<>("copies"));
        try {
            setBooksValueToTable(new BookController().getAllBook());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        AuthorColId.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        AuthorColName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        AuthorColTpNo.setCellValueFactory(new PropertyValueFactory<>("TpNo"));
        AuthorColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        try {
            setAuthorValueToTable(new AuthorController().getAllAuthor());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        publisherColId.setCellValueFactory(new PropertyValueFactory<>("publisherId"));
        publisherColName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        publisherColTpNo.setCellValueFactory(new PropertyValueFactory<>("TpNo"));
        publisherColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        publisherColAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        try {
            setPublisherToTable((new PublisherController().getAllPublisher()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        try {
            loadAuthorIds();
            loadPublisherIds();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        tblBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblBookId.setText(newValue.getBookId());
                txtBookName.setText(newValue.getName());
                cmbAuthor.setValue(newValue.getAuthorId());
                cmbPublisher.setValue(newValue.getPublisherId());

                txtCopies.setText(String.valueOf(newValue.getCopies()));
                btnBookDelete.setDisable(false);
                btnBookUpdate.setDisable(false);
            }
        });
        tblAuthor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblAuthorId.setText(newValue.getAuthorId());
                txtAuthorName.setText(newValue.getName());
                txtAuthorTpNo.setText(String.valueOf(newValue.getTpNo()));
                txtAuthorEmail.setText(String.valueOf(newValue.getEmail()));
                btnAuthorUpdate.setDisable(false);
                btnAuthorDelete.setDisable(false);
            }
        });
        tblPublisher.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblPublisherId.setText(newValue.getPublisherId());
                txtPublisherName.setText(newValue.getName());
                txtPublisherTpNo.setText(String.valueOf(newValue.getTpNo()));
                txtPublisherEmail.setText(String.valueOf(newValue.getEmail()));
                txtPublisherAddress.setText(newValue.getAddress());
                btnPublisherDelete.setDisable(false);
                btnPublisherUpdate.setDisable(false);
            }
        });
        setIds();
        disableBtn();
    }

    private void setIds() {
        try {
            lblBookId.setText(new BookController().getBookId());
            lblAuthorId.setText(new AuthorController().getAuthorId());
            lblPublisherId.setText(new PublisherController().getPublisherId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAuthorIds() throws SQLException, ClassNotFoundException {
        List<String> authorIds = new AuthorController().getAllAuthorIds();
        cmbAuthor.getItems().addAll(authorIds);
    }

    private void loadPublisherIds() throws SQLException, ClassNotFoundException {
        List<String> authorIds = new PublisherController().getAllPublisherIds();
        cmbPublisher.getItems().addAll(authorIds);
    }

    private void storeValidations() {
        map.put(txtBookName, namePattern);
        map.put(txtCopies, copiesPattern);

        map2.put(txtAuthorName, namePattern);
        map2.put(txtAuthorTpNo, tp_NoPattern);
        map2.put(txtAuthorEmail, emailPattern);

        map3.put(txtPublisherName, publisherNamePattern);
        map3.put(txtPublisherTpNo, tp_NoPattern);
        map3.put(txtPublisherEmail, emailPattern);
        map3.put(txtPublisherAddress, AddressPattern);
    }

    public void bookAddOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Book book = new Book(
                lblBookId.getText(),
                txtBookName.getText(),
                cmbAuthor.getValue().toString(),
                cmbPublisher.getValue().toString(),
                0.00,
                Integer.parseInt(txtCopies.getText())
        );

        if (new BookController().saveBook(book)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setBooksValueToTable(new BookController().getAllBook());
            clearTextFieldAndDisableBtn();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    private void setBooksValueToTable(ArrayList<Book> item) {
        ObservableList<BookTm> obList = FXCollections.observableArrayList();
        item.forEach(e -> {
            obList.add(
                    new BookTm(e.getBookId(), e.getName(), e.getAuthorId(), e.getPublisherId(), e.getUnitPrice(), e.getCopies()));
        });
        tblBook.setItems(obList);
    }

    private void setAuthorValueToTable(ArrayList<Author> item) {
        ObservableList<AuthorTm> obList = FXCollections.observableArrayList();
        item.forEach(e -> {
            obList.add(
                    new AuthorTm(e.getAuthorId(), e.getName(), e.getTpNo(), e.getEmail()));
        });
        tblAuthor.setItems(obList);
    }

    public void clearTextFieldAndDisableBtn() {
        txtBookName.clear();
        txtCopies.clear();
        txtAuthorName.clear();
        txtAuthorTpNo.clear();
        txtAuthorEmail.clear();
        txtPublisherName.clear();
        txtPublisherTpNo.clear();
        txtPublisherEmail.clear();
        txtPublisherAddress.clear();
        cmbPublisher.setValue(null);
        cmbAuthor.setValue(null);
        disableBtn();
    }

    public void disableBtn() {
        btnAuthorAdd.setDisable(true);
        btnAuthorDelete.setDisable(true);
        btnAuthorUpdate.setDisable(true);
        btnBookAdd.setDisable(true);
        btnBookDelete.setDisable(true);
        btnBookUpdate.setDisable(true);
        btnPublisherAdd.setDisable(true);
        btnPublisherUpdate.setDisable(true);
        btnPublisherDelete.setDisable(true);
    }

    public void bookDeleteOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to Delete " + txtBookName.getText());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new BookController().deleteBook(lblBookId.getText());
            setBooksValueToTable(new BookController().getAllBook());
            clearTextFieldAndDisableBtn();
        }
    }

    public void bookUpdateOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Book book = new Book(
                lblBookId.getText(),
                txtBookName.getText(),
                cmbAuthor.getValue().toString(),
                cmbPublisher.getValue().toString(),
                0.00,
                Integer.parseInt(txtCopies.getText())
        );

        if (new BookController().updateBook(book)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            setBooksValueToTable(new BookController().getAllBook());
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void addAuthorOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Author author = new Author(
                lblAuthorId.getText(),
                txtAuthorName.getText(),
                txtAuthorTpNo.getText(),
                txtAuthorEmail.getText()
        );

        if (new AuthorController().saveAuthor(author)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setAuthorValueToTable(new AuthorController().getAllAuthor());
            clearTextFieldAndDisableBtn();
            setIds();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    public void deleteAuthorOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to Delete " + txtAuthorName.getText());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new AuthorController().deleteAuthor(lblAuthorId.getText());
            setAuthorValueToTable(new AuthorController().getAllAuthor());
            clearTextFieldAndDisableBtn();
        }
    }

    public void updateAuthorOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        Author author = new Author(
                lblAuthorId.getText(),
                txtAuthorName.getText(),
                txtAuthorTpNo.getText(),
                txtAuthorEmail.getText()
        );

        if (new AuthorController().updateAuthor(author)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            setAuthorValueToTable(new AuthorController().getAllAuthor());
            clearTextFieldAndDisableBtn();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void addPublisherOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Publisher publisher = new Publisher(
                lblPublisherId.getText(),
                txtPublisherName.getText(),
                txtPublisherTpNo.getText(),
                txtPublisherEmail.getText(),
                txtPublisherAddress.getText()
        );

        if (new PublisherController().savePublisher(publisher)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
            setPublisherToTable(new PublisherController().getAllPublisher());
            clearTextFieldAndDisableBtn();
            setIds();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
        }
    }

    private void setPublisherToTable(ArrayList<Publisher> allAuthor) {
        ObservableList<PublisherTm> obList = FXCollections.observableArrayList();
        allAuthor.forEach(e -> {
            obList.add(
                    new PublisherTm(e.getPublisherId(), e.getName(), e.getTpNo(), e.getEmail(), e.getAddress()));
        });
        tblPublisher.setItems(obList);
    }

    public void deletePublisherOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You want to Delete " + txtAuthorName.getText());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new PublisherController().deletePublisher(lblPublisherId.getText());
            setPublisherToTable(new PublisherController().getAllPublisher());
            clearTextFieldAndDisableBtn();
        }
    }

    public void updatePublisherOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Publisher author = new Publisher(
                lblPublisherId.getText(),
                txtPublisherName.getText(),
                txtPublisherTpNo.getText(),
                txtPublisherEmail.getText(),
                txtPublisherAddress.getText()
        );

        if (new PublisherController().updatePublisher(author)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            setPublisherToTable(new PublisherController().getAllPublisher());
            clearTextFieldAndDisableBtn();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void bookTextFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnBookAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Added").showAndWait();
            }
        }
    }

    public void AuthorTextFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map2, btnAuthorAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Added").showAndWait();
            }
        }
    }

    public void PublisherTextFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map3, btnPublisherAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Added").showAndWait();
            }
        }
    }

    public void cancelOnClicked(MouseEvent mouseEvent) {
        clearTextFieldAndDisableBtn();
    }


    public void searchBookOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<Book> books = BookController.searchBook(txtSearchBook.getText());
        setBooksValueToTable((ArrayList<Book>) books);
    }

    public void SearchPublisherOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<Publisher> publishers = PublisherController.searchPublisher(txtTblPublisherSearch.getText());
        setPublisherToTable((ArrayList<Publisher>) publishers);
    }

    public void searchAuthorOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<Author> authors = AuthorController.searchAuthor(txtTblAuthorSearch.getText());
        setAuthorValueToTable((ArrayList<Author>) authors);
    }
}
