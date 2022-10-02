package model;

import java.util.ArrayList;

public class BookReturn {
    private String returnId;
    private String readerId;
    private String returnDate;
    private int bookCount;
    private ArrayList<BookReturnDetail> books;

    public BookReturn() {
    }

    public BookReturn(String returnId, String readerId, String returnDate, int bookCount) {
        this.setReturnId(returnId);
        this.setReaderId(readerId);
        this.setReturnDate(returnDate);
        this.setBookCount(bookCount);
    }

    public BookReturn(String returnId, String readerId, String returnDate, int bookCount, ArrayList<BookReturnDetail> books) {
        this.setReturnId(returnId);
        this.setReaderId(readerId);
        this.setReturnDate(returnDate);
        this.setBookCount(bookCount);
        this.setBooks(books);
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public ArrayList<BookReturnDetail> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BookReturnDetail> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BookReturn{" +
                "returnId='" + returnId + '\'' +
                ", readerId='" + readerId + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", bookCount=" + bookCount +
                ", books=" + books +
                '}';
    }
}
