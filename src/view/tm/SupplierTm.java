package view.tm;

public class SupplierTm {
    private String supplierId;
    private String supName;
    private String supType;
    private String tpNo;
    private String email;
    private String address;

    public SupplierTm() {
    }

    public SupplierTm(String supplierId, String supName, String supType, String tpNo, String email, String address) {
        this.setSupplierId(supplierId);
        this.setSupName(supName);
        this.setSupType(supType);
        this.setTpNo(tpNo);
        this.setEmail(email);
        this.setAddress(address);
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getSupType() {
        return supType;
    }

    public void setSupType(String supType) {
        this.supType = supType;
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
