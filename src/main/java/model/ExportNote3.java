package model;

import java.util.Date;

public class ExportNote3 {
    private int id;
    private String exportNumber;
    private Date exportedAt;
    private String note;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getExportNumber() { return exportNumber; }
    public void setExportNumber(String exportNumber) { this.exportNumber = exportNumber; }
    public Date getExportedAt() { return exportedAt; }
    public void setExportedAt(Date exportedAt) { this.exportedAt = exportedAt; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
