package model;

import java.util.Date;

public class Invoice3 {
    private int id;
    private String invoiceNumber;
    private Date issuedAt;
    private ExportNote3 exportNote;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
    public Date getIssuedAt() { return issuedAt; }
    public void setIssuedAt(Date issuedAt) { this.issuedAt = issuedAt; }
    public ExportNote3 getExportNote() { return exportNote; }
    public void setExportNote(ExportNote3 exportNote) { this.exportNote = exportNote; }
}
