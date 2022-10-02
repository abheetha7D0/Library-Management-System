package model;

public class Publisher {
    private String publisherId;
    private String name;
    private String tpNo;
    private String email;
    private String address;

    public Publisher() {
    }

    public Publisher(String publisherId, String name, String tpNo, String email, String address) {
        this.setPublisherId(publisherId);
        this.setName(name);
        this.setTpNo(tpNo);
        this.setEmail(email);
        this.setAddress(address);
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTpNo() {
        return tpNo;
    }

    public void setTpNo(String tpNo) {
        this.tpNo = tpNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
