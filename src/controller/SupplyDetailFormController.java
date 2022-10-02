package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.BookSupply;
import model.BookSupplyDetail;
import model.Supplier;
import view.tm.BookSupplyTM;
import view.tm.SupplyDetailTm;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SupplyDetailFormController implements Initializable {
    public JFXTextField txtSearch;
    public TableView<BookSupplyTM> tblBookSupply;
    public TableColumn colSupplyId;
    public TableColumn colSupplyDate;
    public TableColumn colSupplierId;
    public TableColumn colCost;
    public PieChart pieChartSupplier;
    public TableView<SupplyDetailTm> tblBookSupplyDetail;
    public TableColumn colName;
    public TableColumn colSupplyQty;
    public TableColumn colPrice;
    public Label lblSupplyId;
    public Label lblSupplierName;

    public Label lblSupplierType;
    public Label lblAnnuallyCost;
    public Label lblMonthlyCost;
    public Label lblDailyCost;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colSupplyId.setCellValueFactory(new PropertyValueFactory<>("SupplyId"));
        colSupplyDate.setCellValueFactory(new PropertyValueFactory<>("SupplyDate"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("SupplyCost"));

        try {
            setBookSupplyValueToTable(new SupplyController().getAllSupplies());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        colName.setCellValueFactory(new PropertyValueFactory<>("Book"));
        colSupplyQty.setCellValueFactory(new PropertyValueFactory<>("supplyQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        try {
            setCost();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        try {
            lordPieChart();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        tblBookSupply.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblSupplyId.setText(newValue.getSupplyId());
                try {
                    setBookSupplyDetailValueToTable(new SupplyDetailController().getSupplyDetail(lblSupplyId.getText()));
                    Supplier sup = new SupplierController().getSupplier(newValue.getSupplierId());
                    lblSupplierName.setText(sup.getSupName());
                    lblSupplierType.setText(sup.getSupType());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }
    public void SearchOnKeyReleased(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<BookSupply> bookSupply = SupplyDetailController.searchSupplierId(txtSearch.getText());
        setBookSupplyValueToTable((ArrayList<BookSupply>) bookSupply);
    }
    private void setCost() throws SQLException, ClassNotFoundException {
        String date;
        String mDate;
        String yDate;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        date = (f.format(new Date()));
        String[] date1 = date.split("-");
        SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
        mDate = (month.format(new Date()));
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        yDate = (year.format(new Date()));
        lblDailyCost.setText(String.valueOf(new SupplyDetailController().getDayCost(date)));

        lblMonthlyCost.setText(String.valueOf(new SupplyDetailController().getMonthlyCost(mDate)));

        lblAnnuallyCost.setText(String.valueOf(new SupplyDetailController().getAnnuallyCost(yDate)));

    }
    private void lordPieChart() throws SQLException, ClassNotFoundException {
        ObservableList<PieChart.Data> supplier = FXCollections.observableArrayList();
        int donate = new SupplyDetailController().getDonatedBooks();
        int supply = new SupplyDetailController().getSupplierSupplyBooks();
        supplier.add(new PieChart.Data("Donator",donate));
        supplier.add(new PieChart.Data("Supplier",supply));
        pieChartSupplier.setData(supplier);
    }
    private void setBookSupplyValueToTable(ArrayList<BookSupply> item) {
        ObservableList<BookSupplyTM> obList = FXCollections.observableArrayList();
        item.forEach(e -> {
            obList.add(
                    new BookSupplyTM(e.getSupplyId(), e.getSupplyDate(), e.getSupplierId(), e.getSupplyCost()));
        });
        tblBookSupply.setItems(obList);
    }
    private void setBookSupplyDetailValueToTable(ArrayList<BookSupplyDetail> item) {
        ObservableList<SupplyDetailTm> obList = FXCollections.observableArrayList();
        item.forEach(e -> {
            obList.add(
                    new SupplyDetailTm(e.getBook(), e.getSupplyQty(), e.getPrice()));
        });
        tblBookSupplyDetail.setItems(obList);
    }
}
