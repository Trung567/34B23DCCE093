package model;

import java.util.Date;

public class ProductCategory3 {
    private int id;
    private String name;
    private String description;
    private Product3[] products = new Product3[0];

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Product3[] getProducts() { return products; }
    public void setProducts(Product3[] products) { this.products = products; }
}
