package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.BookIssueTM;
import view.tm.BookReturnDetailTM;
import view.tm.BookReturnTM;
import view.tm.BookTm;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookReportFormController implements Initializable {

    public JFXTextField txtSearch;
    public TableView<BookIssueTM> tblIssue;
    public TableColumn colIssueId;
    public TableColumn colIssueReaderId;
    public TableColumn colIssueDate;
    public TableColumn colDueDate;
    public TableColumn colCount;
    public TableView<BookTm> tblIssueDetail;
    public TableColumn colBookName;
    public TableColumn colSupplyQty;
    public Label lblReturnId;
    public Label lblReturnReaderName;
    public Label lblDayIssue;
    public Label lblMonthlyIssue;
    public Label lblAnnuallyIssue;
    public TableView<BookReturnTM> tblReturn;
    public TableColumn colReturnId;
    public TableColumn colReaderId;
    public TableColumn cilReaderReturnDate;
    public TableColumn colRDueDate;
    public TableColumn colRCount;
    public TableView<BookReturnDetailTM> tblReturnDetail;
    public TableColumn colReturnBookName;
    public TableColumn colReport;
    public TableColumn colFinePrice;
    public JFXButton btnTopTen;
    public Label lblDayReturn;
    public Label lblMonthlyReturn;
    public Label lblAnnuallyReturn;
    public Label lblIssueId;
    public Label lblIssueReaderName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colIssueId.setCellValueFactory(new PropertyValueFactory<>("issueId"));
        colIssueReaderId.setCellValueFactory(new PropertyValueFactory<>("readerId"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colCount.setCellValueFactory(new PropertyValueFactory<>("bookCount"));

        colReturnId.setCellValueFactory(new PropertyValueFactory<>("returnId"));
        colReaderId.setCellValueFactory(new PropertyValueFactory<>("readerId"));
        cilReaderReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colRDueDate.setCellValueFactory(new PropertyValueFactory<>("bookCount"));
        colRCount.setCellValueFactory(new PropertyValueFactory<>("bookCount"));

        colBookName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        colReturnBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colReport.setCellValueFactory(new PropertyValueFactory<>("report"));
        colFinePrice.setCellValueFactory(new PropertyValueFactory<>("FinePrice"));

        try {
            setCost();
            setBookIssueValueToTable(new BookReportController().getAllBookIssues());
            setBookReturnValueToTable(new BookReportController().getAllBookReturns());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tblIssue.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblIssueId.setText(newValue.getIssueId());
                try {
                    setBookIssueDetailValueToTable(new BookReportController().getIssueDetail(lblIssueId.getText()));
                    Reader reader = new BookReportController().getReader(newValue.getReaderId());
                    lblIssueReaderName.setText(reader.getName());

                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        tblReturn.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblReturnId.setText(newValue.getReturnId());
                try {
                    setBookReturnDetailValueToTable(new BookReportController().getReturnDetail(lblReturnId.getText()));
                    Reader reader = new BookReportController().getReader(newValue.getReaderId());
                    lblReturnReaderName.setText(reader.getName());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }



    public void SearchOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        ArrayList<BookIssue> bookIssues = BookReportController.searchBookIssues(txtSearch.getText());
        setBookIssueValueToTable(bookIssues);
        ArrayList<BookReturn> bookReturns = BookReportController.searchBookReturns(txtSearch.getText());
        setBookReturnValueToTable(bookReturns);
    }
    private void setCost() throws SQLException, ClassNotFoundException {
        String date;
        String mDate;
        String yDate;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        date = (f.format(new Date()));
        SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
        mDate = (month.format(new Date()));
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        yDate = (year.format(new Date()));
        lblDayIssue.setText(String.valueOf(new BookReportController().getDayIssueBooks(date)));

        lblMonthlyIssue.setText(String.valueOf(new BookReportController().getMonthlyIssueBooks(mDate)));

        lblAnnuallyIssue.setText(String.valueOf(new BookReportController().getAnnuallyIssueBooks(yDate)));

        lblDayReturn.setText(String.valueOf(new BookReportController().getDayReturnBooks(date)));

        lblMonthlyReturn.setText(String.valueOf(new BookReportController().getMonthlyReturnBooks(mDate)));

        lblAnnuallyReturn.setText(String.valueOf(new BookReportController().getAnnuallyReturnBooks(yDate)));
    }


    public void openTopTenBooksOnClicked(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/TopTenBooksReport.jrxml"));
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void setBookIssueValueToTable(ArrayList<BookIssue> bookIssues) {
        ObservableList<BookIssueTM> obList = FXCollections.observableArrayList();
        bookIssues.forEach(e -> {
            obList.add(
                    new BookIssueTM(e.getIssueId(), e.getReaderId(), e.getIssueDate(), e.getDueDate(),e.getBookCount()));
        });
        tblIssue.setItems(obList);
    }
    private void setBookReturnValueToTable(ArrayList<BookReturn> allBookReturns) {
        ObservableList<BookReturnTM> obList = FXCollections.observableArrayList();
        allBookReturns.forEach(e -> {
            obList.add(
                    new BookReturnTM(e.getReturnId(), e.getReaderId(), e.getReturnDate(), e.getBookCount()));
        });
        tblReturn.setItems(obList);
    }
    private void setBookIssueDetailValueToTable(ArrayList<Book> allBookReturns) {
        ObservableList<BookTm> obList = FXCollections.observableArrayList();
        allBookReturns.forEach(e -> {
            obList.add(
                    new BookTm(e.getName()));
        });
        tblIssueDetail.setItems(obList);
    }
    private void setBookReturnDetailValueToTable(ArrayList<BookReturnDetail> allBookReturns) {
        ObservableList<BookReturnDetailTM> obList = FXCollections.observableArrayList();
        allBookReturns.forEach(e -> {
            obList.add(
                    new BookReturnDetailTM(e.getName(),e.getCopies(), e.getReport(), e.getFinePrice()));
        });
        tblReturnDetail.setItems(obList);
    }
}
