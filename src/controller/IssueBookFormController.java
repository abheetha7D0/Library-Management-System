package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import view.tm.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;



public class IssueBookFormController implements Initializable {

    public Label lblBookName;
    public Label lblAvailable;
    public TableView<BrBookTm> tblBook;

    public TableColumn colName;
    public TableColumn colAvailability;
    public TableColumn colAuthor;
    public TableColumn colBooksId;
    public Label lblReaderName;
    public JFXComboBox<String> cmbReaderId;
    public String date;
    public DatePicker dtPikerDueDate;
    public JFXButton btnGiveBook;
    public TableColumn colBookId;
    public TableColumn colReaderId;
    public JFXComboBox<String> cmbBookId;
    public Label lblPublisherId;
    public Label lblAuthorId;
    public JFXButton btnReturnBook;

    public Label lblBookIssueId;
    public TableColumn colBookName;
    public TableColumn colAuthorId;
    public TableColumn colPublisherId;
    public JFXButton btnIssueBook;
    public JFXButton btnAddToList;
    public JFXButton btnClear;
    public TableView <BookTm>tblBookIssue;
    public Label lblBooks;
    public JFXTextField txtSearchBook;
    public AnchorPane bookIssueContext;
    private String bookId = null;
    private String readerId = null;
    private String dueDate = null;
    int cartSelectedRowForRemove = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBooksId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));

        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAuthorId.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        colPublisherId.setCellValueFactory(new PropertyValueFactory<>("publisherId"));


        try {
            loadBookIds();
            loadReaderIds();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        cmbReaderId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setReaderData(newValue);
                readerId = newValue;
                dtPikerDueDate.requestFocus();
                validate();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
        cmbBookId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setBookData(newValue);
                bookId = newValue;
                cmbReaderId.requestFocus();
                validate();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });


        tblBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cmbBookId.setValue(newValue.getBookId());

            }
        });
        tblBookIssue.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
            btnClear.setDisable(false);
        });
        setBookIssueId();
        try {
            setBookValueToTable(new BookController().getAllBook());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        validate();
    }

    private void loadBookIds() throws SQLException, ClassNotFoundException {
        List<String> suppliers = new BookController().getAllBookIds();
        cmbBookId.getItems().addAll(suppliers);
    }

    private void loadReaderIds() throws SQLException, ClassNotFoundException {
        List<String> readerIds = new ReaderController().getAllReaderIds();
        cmbReaderId.getItems().addAll(readerIds);
    }

    private void setBookData(String bookId) throws SQLException, ClassNotFoundException {
        Book book = new BookController().getBook(bookId);
        if (book == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            lblBookName.setText(book.getName());
            lblAuthorId.setText(book.getAuthorId());
            lblPublisherId.setText(book.getPublisherId());
            lblAvailable.setText(0 < book.getCopies() ? "Available" : "Not Available");
        }

    }



    private void setReaderData(String readerId) throws SQLException, ClassNotFoundException {
        Reader reader = new ReaderController().getReader(readerId);
        if (reader == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            lblReaderName.setText(reader.getName());
        }
    }

    private void validate() {
        if (readerId != null && bookId != null && dueDate != null) {
            btnAddToList.setDisable(false);
        }
    }

    private void clearTextAndDisableBtn() {
        cmbReaderId.setValue(null);
        cmbBookId.setValue(null);
        cmbReaderId.setValue(null);
        dtPikerDueDate.setValue(null);

        btnGiveBook.setDisable(true);
        btnReturnBook.setDisable(true);

    }

    private void setBookIssueId() {
        try {

            lblBookIssueId.setText(new IssueBookController().getBookIssueId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setBookValueToTable(ArrayList<Book> books) {
        ObservableList<BrBookTm> obList = FXCollections.observableArrayList();
        books.forEach(e -> {
            obList.add(
                    new BrBookTm(e.getBookId(), e.getName(), (0 < e.getCopies() ? "Available" : "Not Available"), e.getAuthorId()));
        });
        tblBook.setItems(obList);
    }


    public void datePickOnClicked(ActionEvent actionEvent) {
        LocalDate pickDueDate = dtPikerDueDate.getValue();
        if (pickDueDate != null) {
            dueDate = pickDueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            validate();
        }
    }

    public void searchBookOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Book> book = new IssueBookController().searchBooks(txtSearchBook.getText());
        setBookValueToTable(book);
    }

    ObservableList<BookTm> obList= FXCollections.observableArrayList();
    public void addToListOnClicked(MouseEvent mouseEvent) {
        BookTm tm = new BookTm(
                cmbBookId.getValue(),
                lblBookName.getText(),
                lblAuthorId.getText(),
                lblPublisherId.getText(),
                1
        );
        int rowNumber=isExists(tm,obList);
        if (rowNumber==-1){
            obList.add(tm);
        }else{
            new Alert(Alert.AlertType.WARNING, "Already listed").show();
        }
        tblBookIssue.setItems(obList);
        calculateBooks();
        btnIssueBook.setDisable(false);
    }
    void calculateBooks(){

            int books=0;
            for (BookTm tm:obList
            ) {
                books+=tm.getCopies();
            }
            lblBooks.setText(String.valueOf(books));

    }
    private int isExists(BookTm tm,ObservableList<BookTm> obList){
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getBookId().equals(obList.get(i).getBookId())){
                return i;
            }
        }
        return -1;
    }
    public void issueBookOnClicked(MouseEvent mouseEvent) {
        ArrayList<BookIssueDetail> bookIssueDetails= new ArrayList<>();
        int books=0;
        for (BookTm tempTm:obList
        ) {
            books+=tempTm.getCopies();
            bookIssueDetails.add(new BookIssueDetail(tempTm.getBookId(),1));
        }


        BookIssue book= new BookIssue(lblBookIssueId.getText(),
                cmbReaderId.getValue(),
                date,
                dueDate,
                Integer.parseInt(lblBooks.getText()),
                bookIssueDetails

        );

        if (new IssueBookController().IssueBook(book)){
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            setBookIssueId();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }
    public void clearOnClicked(MouseEvent mouseEvent) {
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            obList.remove(cartSelectedRowForRemove);
            calculateBooks();
            tblBookIssue.refresh();
        }
    }

    public void openTransactionDetailOnClicked(MouseEvent mouseEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/BookReportForm.fxml"));
        pane = fxmlLoader.load();
        bookIssueContext.getChildren().setAll(pane);
    }
}
