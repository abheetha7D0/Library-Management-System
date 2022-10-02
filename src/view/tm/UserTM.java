package view.tm;

public class UserTM {
    private String userId;
    private String userName;
    private String role;
    private String tpNo;
    private String email;
    private String address;

    public UserTM() {
    }

    public UserTM(String userId, String userName, String role, String tpNo, String email, String address) {
        this.setUserId(userId);
        this.setUserName(userName);
        this.setRole(role);
        this.setTpNo(tpNo);
        this.setEmail(email);
        this.setAddress(address);
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
