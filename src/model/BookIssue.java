package model;

import java.util.ArrayList;

public class BookIssue {
    private String issueId;
    private String readerId;
    private String issueDate;
    private String dueDate;
    private int bookCount;
    private ArrayList<BookIssueDetail> books;

    public BookIssue(String issueId, String readerId, String issueDate, String dueDate, int bookCount) {
        this.issueId = issueId;
        this.readerId = readerId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.bookCount = bookCount;
    }

    public BookIssue(String issueId, String readerId, String issueDate, String dueDate, int bookCount, ArrayList<BookIssueDetail> books) {
        this.setIssueId(issueId);
        this.setReaderId(readerId);
        this.setIssueDate(issueDate);
        this.setDueDate(dueDate);
        this.setBookCount(bookCount);
        this.setBooks(books);
    }

    public BookIssue() {
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public ArrayList<BookIssueDetail> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BookIssueDetail> books) {
        this.books = books;
    }
}
