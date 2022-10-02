package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.*;
import view.tm.AddToListBookReturnTm;
import view.tm.IssueReaderAndDetailTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ReturnBookFormController implements Initializable {

    public Label lblBookName;
    public Label lblReaderName;
    public JFXComboBox<String> cmbReaderId;
    public JFXButton btnAddToList;
    public Label lblAuthorId;
    public Label lblPublisherId;
    public Label lblBookIssueId;
    public JFXButton btnClear;
    public Label lblBooks;
    public JFXTextArea txtFldReport;
    public TableView<AddToListBookReturnTm> tblBookReturn;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colAuthorId;
    public TableColumn colPublisherId;
    public TableColumn colReport;
    public JFXComboBox<String> cmbBookId;
    public TableView<IssueReaderAndDetailTM> tblIssueBook;
    public TableColumn colReaderId;
    public TableColumn colBooksId;
    public TableColumn colBooksName;
    public JFXButton btnBookReturn;
    public TableColumn colDueDate;
    public Label lblBookReturn;
    public JFXTextField txtSearch;
    public JFXTextField txtFinePrice;
    public TableColumn colFinePrice;
    public AnchorPane returnBookContext;
    private String bookId = null;
    private String readerId = null;
    private String report = null;
    private Date dueDate = null;
    public String date;
    int cartSelectedRowForRemove = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colReaderId.setCellValueFactory(new PropertyValueFactory<>("readerId"));
        colBooksId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBooksName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("DueDate"));

        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAuthorId.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        colPublisherId.setCellValueFactory(new PropertyValueFactory<>("publisherId"));
        colReport.setCellValueFactory(new PropertyValueFactory<>("report"));
        colFinePrice.setCellValueFactory(new PropertyValueFactory<>("fineName"));
        try {
            loadReaderIds();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        setBookReaderId();
        tblBookReturn.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
            btnClear.setDisable(false);
        });
        cmbReaderId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setReaderData(newValue);
                readerId = newValue;
                loadBookIds(readerId);
                tblBookReturn.getItems().clear();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
        cmbBookId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setBookData(newValue);
                bookId = newValue;
                validate();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
        try {
            setIssueBooksValueToTable(new IssueBookController().getAllIssueReaderAndDetail());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        tblIssueBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cmbReaderId.setValue(newValue.getReaderId());
            cmbBookId.setValue(newValue.getBookId());
            btnAddToList.setDisable(false);
            try {
                checkLateReturn();
            } catch (SQLException | ClassNotFoundException | ParseException throwables) {
                throwables.printStackTrace();
            }
        });
        try {
            checkLateReturn();
        } catch (SQLException | ClassNotFoundException | ParseException throwables) {
            throwables.printStackTrace();
        }
        validate();
    }
    private void setBookReaderId() {
        try {

            lblBookReturn.setText(new ReturnBookController().getBookReturnId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void loadBookIds(String readerId) throws SQLException, ClassNotFoundException {
        List<String> ids = new IssueBookController().getAllIssueBookIds(readerId);
        cmbBookId.getItems().addAll(ids);
    }

    private void loadReaderIds() throws SQLException, ClassNotFoundException {
        List<String> readerIds = new IssueBookController().getAllIssueReadersIds();
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

    private void checkLateReturn() throws SQLException, ClassNotFoundException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<IssueReaderAndDetail> issueReaderAndDetail = new IssueBookController().getAllIssueReaderAndDetail();
        for (IssueReaderAndDetail i :
                issueReaderAndDetail) {
            if (cmbBookId.getValue() != null && cmbReaderId.getValue() != null)
                if (cmbReaderId.getValue().equals(i.getReaderId()) && cmbBookId.getValue().equals(i.getBookId())) {
                    dueDate = sdf.parse(i.getDueDate());
                    Date today = sdf.parse(date);
                    if (dueDate.compareTo(today) < 0) {
                        txtFldReport.setText("Late return");
                    }
                }
        }
    }


    public void searchBookOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<IssueReaderAndDetail> issueReaderAndDetails = IssueBookController.searchBook(txtSearch.getText());
        setIssueBooksValueToTable((ArrayList<IssueReaderAndDetail>) issueReaderAndDetails);
    }

    public void returnBookOnClicked(MouseEvent mouseEvent) {
        ArrayList<BookReturnDetail> bookReturnDetails= new ArrayList<>();
        int books=0;
        for (AddToListBookReturnTm tempTm:obList
        ) {
            books+=tempTm.getCopies();
            bookReturnDetails.add(new BookReturnDetail(tempTm.getBookId(),1, tempTm.getReport(),tempTm.getFinePrice()));
        }


        BookReturn book= new BookReturn(lblBookReturn.getText(),
                cmbReaderId.getValue(),
                date,
                Integer.parseInt(lblBooks.getText()),
                bookReturnDetails
        );

        if (new ReturnBookController().returnBook(book)){
            new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            setBookReaderId();
            tblBookReturn.getItems().clear();
            btnAddToList.setDisable(true);
            btnBookReturn.setDisable(false);
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    ObservableList<AddToListBookReturnTm> obList = FXCollections.observableArrayList();

    public void addToListOnClicked(MouseEvent mouseEvent) {
        AddToListBookReturnTm tm = new AddToListBookReturnTm(
                cmbBookId.getValue(),
                lblBookName.getText(),
                lblAuthorId.getText(),
                lblPublisherId.getText(),
                1,
                txtFldReport.getText(),
                Double.parseDouble(txtFinePrice.getText())
        );
        int rowNumber = isExists(tm, obList);
        if (rowNumber == -1) {
            obList.add(tm);
        } else {
            new Alert(Alert.AlertType.WARNING, "Already listed").show();
        }
        tblBookReturn.setItems(obList);
        calculateBooks();
        btnBookReturn.setDisable(false);
    }

    private int isExists(AddToListBookReturnTm tm, ObservableList<AddToListBookReturnTm> obList) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getBookId().equals(obList.get(i).getBookId())) {
                return i;
            }
        }
        return -1;
    }

    void calculateBooks() {

        int books = 0;
        for (AddToListBookReturnTm tm : obList
        ) {
            books += tm.getCopies();
        }
        lblBooks.setText(String.valueOf(books));
    }

    public void clearOnClicked(MouseEvent mouseEvent) {
        if (cartSelectedRowForRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            obList.remove(cartSelectedRowForRemove);
            calculateBooks();
            tblBookReturn.refresh();
        }
    }

    private void setIssueBooksValueToTable(ArrayList<IssueReaderAndDetail> attendances) {
        ObservableList<IssueReaderAndDetailTM> obList = FXCollections.observableArrayList();
        attendances.forEach(e -> {
            obList.add(
                    new IssueReaderAndDetailTM(e.getReaderId(), e.getBookId(), e.getBookName(), e.getDueDate()));
        });
        tblIssueBook.setItems(obList);
    }
    public void validate() {
        if (cmbBookId.getValue() != null && cmbReaderId != null){
            btnAddToList.setDisable(false);
        }
    }


    public void OpenTransactionDetailOnClicked(MouseEvent mouseEvent) throws IOException {
        AnchorPane pane;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/BookReportForm.fxml"));
        pane = fxmlLoader.load();
        returnBookContext.getChildren().setAll(pane);
    }
}
