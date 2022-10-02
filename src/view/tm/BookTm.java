package view.tm;

public class BookTm {
    private String bookId;
    private String name;
    private String authorId;
    private String publisherId;
    private double UnitPrice;
    private int copies;

    public BookTm(String name) {
        this.name = name;
    }

    public BookTm(String bookId, String name, String authorId, String publisherId, int copies) {
        this.bookId = bookId;
        this.name = name;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.copies = copies;
    }

    public BookTm() {
    }

    public BookTm(String bookId, String name, String authorId, String publisherId, double unitPrice, int copies) {
        this.setBookId(bookId);
        this.setName(name);
        this.setAuthorId(authorId);
        this.setPublisherId(publisherId);
        this.setUnitPrice(unitPrice);
        this.setCopies(copies);
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
