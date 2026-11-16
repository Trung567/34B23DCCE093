package model;

import java.util.Date;

public class Supplier3 {
    private int id;
    private String supplierCode;
    private String supplierName;
    private String address;
    private String phone;
    private String email;
    private String taxCode;
    private String description;
    private GoodsReceipt3[] goodsReceipt = new GoodsReceipt3[0];

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSupplierCode() { return supplierCode; }
    public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTaxCode() { return taxCode; }
    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public GoodsReceipt3[] getGoodsReceipt() { return goodsReceipt; }
    public void setGoodsReceipt(GoodsReceipt3[] goodsReceipt) { this.goodsReceipt = goodsReceipt; }
}
