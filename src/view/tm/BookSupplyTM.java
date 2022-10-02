package view.tm;

import model.BookSupplyDetail;

import java.util.ArrayList;

public class BookSupplyTM {
    private String supplyId;
    private String supplyDate;
    private String supplierId;
    private double supplyCost;
    private ArrayList<BookSupplyDetail> books;

    public BookSupplyTM() {
    }

    public BookSupplyTM(String supplyId, String supplyDate, String supplierId, double supplyCost) {
        this.supplyId = supplyId;
        this.supplyDate = supplyDate;
        this.supplierId = supplierId;
        this.supplyCost = supplyCost;
    }

    public BookSupplyTM(String supplyId, String supplyDate, String supplierId, double supplyCost, ArrayList<BookSupplyDetail> books) {
        this.setSupplyId(supplyId);
        this.setSupplyDate(supplyDate);
        this.setSupplierId(supplierId);
        this.setSupplyCost(supplyCost);
        this.setBooks(books);
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public String getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(String supplyDate) {
        this.supplyDate = supplyDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public double getSupplyCost() {
        return supplyCost;
    }

    public void setSupplyCost(double supplyCost) {
        this.supplyCost = supplyCost;
    }

    public ArrayList<BookSupplyDetail> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BookSupplyDetail> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BookSupply{" +
                "supplyId='" + supplyId + '\'' +
                ", supplyDate='" + supplyDate + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplyCost=" + supplyCost +
                ", books=" + books +
                '}';
    }
}
