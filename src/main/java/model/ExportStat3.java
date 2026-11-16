package model;

import java.util.Date;

public class ExportStat3 extends ExportNote3 {
    private Date startDate;
    private Date endDate;
    private int exportCount;
    private float totalOrderValue;

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public int getExportCount() { return exportCount; }
    public void setExportCount(int exportCount) { this.exportCount = exportCount; }
    public float getTotalOrderValue() { return totalOrderValue; }
    public void setTotalOrderValue(float totalOrderValue) { this.totalOrderValue = totalOrderValue; }
}
