package model;

import java.util.Date;

public class Order3 {
    private int id;
    private String orderNumber;
    private Date orderedAt;
    private String deliveryAddress;
    private float totalAmount;
    private String status;
    private Date approvedAt;
    private OrderLine3[] orderLines = new OrderLine3[0];
    private Invoice3 invoice;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public Date getOrderedAt() { return orderedAt; }
    public void setOrderedAt(Date orderedAt) { this.orderedAt = orderedAt; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public float getTotalAmount() { return totalAmount; }
    public void setTotalAmount(float totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getApprovedAt() { return approvedAt; }
    public void setApprovedAt(Date approvedAt) { this.approvedAt = approvedAt; }
    public OrderLine3[] getOrderLines() { return orderLines; }
    public void setOrderLines(OrderLine3[] orderLines) { this.orderLines = orderLines; }
    public Invoice3 getInvoice() { return invoice; }
    public void setInvoice(Invoice3 invoice) { this.invoice = invoice; }
}
