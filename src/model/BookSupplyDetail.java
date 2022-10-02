package model;

public class BookSupplyDetail {

    private String book;
    private int supplyQty;
    private double price;


    public BookSupplyDetail() {
    }

    public BookSupplyDetail(String book, int supplyQty, double price) {
        this.setBook(book);
        this.setSupplyQty(supplyQty);
        this.setPrice(price);
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getSupplyQty() {
        return supplyQty;
    }

    public void setSupplyQty(int supplyQty) {
        this.supplyQty = supplyQty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
