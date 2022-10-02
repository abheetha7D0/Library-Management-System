package model;

public class BookReturnDetail {
    private String name;
    private int copies;
    private String report;
    private double finePrice;

    public BookReturnDetail() {
    }

    public BookReturnDetail(String name, int copies, String report, double finePrice) {
        this.setName(name);
        this.setCopies(copies);
        this.setReport(report);
        this.setFinePrice(finePrice);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
