package model;

public class IssueReaderAndDetail {
    private String readerId;
    private String bookId;
    private String bookName;
    private String dueDate;

    public IssueReaderAndDetail() {
    }

    public IssueReaderAndDetail(String readerId, String bookId, String bookName, String dueDate) {
        this.setReaderId(readerId);
        this.setBookId(bookId);
        this.setBookName(bookName);
        this.setDueDate(dueDate);
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
