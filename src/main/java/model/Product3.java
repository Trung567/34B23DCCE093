package model;

import java.util.Date;

public class Product3 {
    private int id;
    private String productCode;
    private String productName;
    private String description;
    private String unitOfMeasure;
    private float sellingPrice;
    private int stockQuantity;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUnitOfMeasure() { return unitOfMeasure; }
    public void setUnitOfMeasure(String unitOfMeasure) { this.unitOfMeasure = unitOfMeasure; }
    public float getSellingPrice() { return sellingPrice; }
    public void setSellingPrice(float sellingPrice) { this.sellingPrice = sellingPrice; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
}
