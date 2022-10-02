package model;

public class Author {
    private String authorId;
    private String name;
    private String TpNo;
    private String email;

    public Author() {
    }

    public Author(String authorId, String name, String tpNo, String email) {
        this.setAuthorId(authorId);
        this.setName(name);
        setTpNo(tpNo);
        this.setEmail(email);
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTpNo() {
        return TpNo;
    }

    public void setTpNo(String tpNo) {
        TpNo = tpNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
