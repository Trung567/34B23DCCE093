package model;

import java.util.Date;

public class CustomerStat3 extends Customer3 {
    private Date startDate;
    private Date endDate;
    private int orderCount;
    private float totalOrderValue;

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public int getOrderCount() { return orderCount; }
    public void setOrderCount(int orderCount) { this.orderCount = orderCount; }
    public float getTotalOrderValue() { return totalOrderValue; }
    public void setTotalOrderValue(float totalOrderValue) { this.totalOrderValue = totalOrderValue; }
}
