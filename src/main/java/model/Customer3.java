package model;

import java.util.Date;

public class Customer3 extends Member3 {
    private String customerCode;
    private Order3[] orders = new Order3[0];

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }
    public Order3[] getOrders() { return orders; }
    public void setOrders(Order3[] orders) { this.orders = orders; }
}
