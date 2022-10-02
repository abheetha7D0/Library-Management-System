package model;

public class ReaderIssueStatus {
    private String readerId;
    private String name;
    private String status;
    private String books;

    public ReaderIssueStatus() {
    }

    public ReaderIssueStatus(String readerId, String name, String status, String books) {
        this.setReaderId(readerId);
        this.setName(name);
        this.setStatus(status);
        this.setBooks(books);
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }
}
