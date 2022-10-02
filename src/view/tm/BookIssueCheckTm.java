package view.tm;

public class BookIssueCheckTm {
    private String bookId;
    private String bookName;
    private String authorId;
    private String publisherId;

    public BookIssueCheckTm() {
    }

    public BookIssueCheckTm(String bookId, String bookName, String authorId, String publisherId) {
        this.setBookId(bookId);
        this.setBookName(bookName);
        this.setAuthorId(authorId);
        this.setPublisherId(publisherId);
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
}
