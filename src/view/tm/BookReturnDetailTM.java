package view.tm;

public class BookReturnDetailTM {
    private String name;
    private int copies;
    private String report;
    private double FinePrice;

    public BookReturnDetailTM(String name, String report, double finePrice) {
        this.setName(name);
        this.setReport(report);
        setFinePrice(finePrice);
    }

    public BookReturnDetailTM(String name, int copies, String report, double finePrice) {
        this.setName(name);
        this.setCopies(copies);
        this.setReport(report);
        setFinePrice(finePrice);
    }

    public BookReturnDetailTM() {
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
        return FinePrice;
    }

    public void setFinePrice(double finePrice) {
        FinePrice = finePrice;
    }
}
