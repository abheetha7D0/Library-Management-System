package view.tm;

public class BookReturnTM {
    private String returnId;
    private String readerId;
    private String returnDate;
    private int bookCount;

    public BookReturnTM() {
    }

    public BookReturnTM(String returnId, String readerId, String returnDate, int bookCount) {
        this.setReturnId(returnId);
        this.setReaderId(readerId);
        this.setReturnDate(returnDate);
        this.setBookCount(bookCount);
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
}
