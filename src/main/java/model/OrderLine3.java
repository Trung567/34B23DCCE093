package model;

import java.util.Date;

public class OrderLine3 {
    private int id;
    private int quantity;
    private float unitPrice;
    private float lineTotal;
    private Product3 product;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public float getUnitPrice() { return unitPrice; }
    public void setUnitPrice(float unitPrice) { this.unitPrice = unitPrice; }
    public float getLineTotal() { return lineTotal; }
    public void setLineTotal(float lineTotal) { this.lineTotal = lineTotal; }
    public Product3 getProduct() { return product; }
    public void setProduct(Product3 product) { this.product = product; }
}
