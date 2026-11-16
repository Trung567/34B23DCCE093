package model;

import java.util.Date;

public class SupplierStat3 extends Supplier3 {
    private Date startDate;
    private Date endDate;
    private int receiptCount;
    private float totalImportValue;

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public int getReceiptCount() { return receiptCount; }
    public void setReceiptCount(int receiptCount) { this.receiptCount = receiptCount; }
    public float getTotalImportValue() { return totalImportValue; }
    public void setTotalImportValue(float totalImportValue) { this.totalImportValue = totalImportValue; }
}
