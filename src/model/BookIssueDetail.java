package model;

public class BookIssueDetail {

    private String bookId;
    private int copies;
    public BookIssueDetail() {
    }

    public BookIssueDetail(String bookId, int copies) {
        this.setBookId(bookId);
        this.setCopies(copies);
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
