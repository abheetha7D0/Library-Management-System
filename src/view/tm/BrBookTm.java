package view.tm;

public class BrBookTm {
private String bookId;
private String name;
private String Availability;
private String Author;

    public BrBookTm() {
    }

    public BrBookTm(String bookId, String name, String availability, String author) {
        this.setBookId(bookId);
        this.setName(name);
        setAvailability(availability);
        setAuthor(author);
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

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }
}
