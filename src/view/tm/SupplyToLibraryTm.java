package view.tm;

public class SupplyToLibraryTm {
    private String bookId;
    private String bookName;
    private String authorId;
    private String publisherId;
    private int copies;
    private double unitPrice;
    private double total;

    public SupplyToLibraryTm() {
    }

    public SupplyToLibraryTm(String bookId, String bookName, String authorId, String publisherId, int copies, double unitPrice, double total) {
        this.setBookId(bookId);
        this.setBookName(bookName);
        this.setAuthorId(authorId);
        this.setPublisherId(publisherId);
        this.setCopies(copies);
        this.setUnitPrice(unitPrice);
        this.setTotal(total);
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

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
