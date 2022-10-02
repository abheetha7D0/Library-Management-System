package view.tm;

public class BookIssueTM {
    private String issueId;
    private String readerId;
    private String issueDate;
    private String dueDate;
    private int bookCount;

    public BookIssueTM() {
    }

    public BookIssueTM(String issueId, String readerId, String issueDate, String dueDate, int bookCount) {
        this.setIssueId(issueId);
        this.setReaderId(readerId);
        this.setIssueDate(issueDate);
        this.setDueDate(dueDate);
        this.setBookCount(bookCount);
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
}
