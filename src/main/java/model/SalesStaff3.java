package model;

import java.util.Date;

public class SalesStaff3 extends Employee3 {
    private Order3[] approvedOrders = new Order3[0];
    private ExportNote3[] exportNotes = new ExportNote3[0];

    public Order3[] getApprovedOrders() { return approvedOrders; }
    public void setApprovedOrders(Order3[] approvedOrders) { this.approvedOrders = approvedOrders; }
    public ExportNote3[] getExportNotes() { return exportNotes; }
    public void setExportNotes(ExportNote3[] exportNotes) { this.exportNotes = exportNotes; }
}
