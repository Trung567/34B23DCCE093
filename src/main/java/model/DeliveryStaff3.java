package model;

import java.util.Date;

public class DeliveryStaff3 extends Employee3 {
    private ExportNote3[] exportNotes = new ExportNote3[0];

    public ExportNote3[] getExportNotes() { return exportNotes; }
    public void setExportNotes(ExportNote3[] exportNotes) { this.exportNotes = exportNotes; }
}
