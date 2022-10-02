package view.tm;

public class AddToListBookReturnTm {
    private String bookId;
    private String name;
    private String authorId;
    private String publisherId;
    private int copies;
    private String report;
    private double finePrice;

    public AddToListBookReturnTm() {
    }

    public AddToListBookReturnTm(String bookId, String name, String authorId, String publisherId, int copies, String report, double finePrice) {
        this.setBookId(bookId);
        this.setName(name);
        this.setAuthorId(authorId);
        this.setPublisherId(publisherId);
        this.setCopies(copies);
        this.setReport(report);
        this.setFinePrice(finePrice);
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

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public double getFinePrice() {
        return finePrice;
    }

    public void setFinePrice(double finePrice) {
        this.finePrice = finePrice;
    }
}
