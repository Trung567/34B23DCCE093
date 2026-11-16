package model;

import java.util.Date;

public class GoodsReceiptLine3 {
    private int id;
    private int quantity;
    private float unitCost;
    private float lineTotal;
    private Product3 product;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public float getUnitCost() { return unitCost; }
    public void setUnitCost(float unitCost) { this.unitCost = unitCost; }
    public float getLineTotal() { return lineTotal; }
    public void setLineTotal(float lineTotal) { this.lineTotal = lineTotal; }
    public Product3 getProduct() { return product; }
    public void setProduct(Product3 product) { this.product = product; }
}
