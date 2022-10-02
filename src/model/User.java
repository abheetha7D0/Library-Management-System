package model;



public class User {
    private String userId;
    private String userName;
    private String password;
    private String role;
    private String TpNo;
    private String Email;
    private String Address;

    public User() {
    }
    public User(String userId, String userName, String password, String role, String tpNo, String email, String address) {
        this.setUserId(userId);
        this.setUserName(userName);
        this.setPassword(password);
        this.setRole(role);
        setTpNo(tpNo);
        setEmail(email);
        setAddress(address);
    }

    public User(String userId, String userName, String role, String tpNo, String email, String address) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
        TpNo = tpNo;
        Email = email;
        Address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTpNo() {
        return TpNo;
    }

    public void setTpNo(String tpNo) {
        TpNo = tpNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
