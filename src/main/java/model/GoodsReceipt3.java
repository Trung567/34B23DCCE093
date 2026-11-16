package model;

import java.util.Date;

public class GoodsReceipt3 {
    private int id;
    private String receiptNumber;
    private Date receivedAt;
    private String note;
    private float totalAmount;
    private GoodsReceiptLine3[] goodsReceiptLines = new GoodsReceiptLine3[0];

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }
    public Date getReceivedAt() { return receivedAt; }
    public void setReceivedAt(Date receivedAt) { this.receivedAt = receivedAt; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public float getTotalAmount() { return totalAmount; }
    public void setTotalAmount(float totalAmount) { this.totalAmount = totalAmount; }
    public GoodsReceiptLine3[] getGoodsReceiptLines() { return goodsReceiptLines; }
    public void setGoodsReceiptLines(GoodsReceiptLine3[] goodsReceiptLines) { this.goodsReceiptLines = goodsReceiptLines; }
}
