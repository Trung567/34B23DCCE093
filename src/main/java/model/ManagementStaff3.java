package model;

import java.util.Date;

public class ManagementStaff3 extends Employee3 {
    private GoodsReceipt3[] goodsReceipts = new GoodsReceipt3[0];

    public GoodsReceipt3[] getGoodsReceipts() { return goodsReceipts; }
    public void setGoodsReceipts(GoodsReceipt3[] goodsReceipts) { this.goodsReceipts = goodsReceipts; }
}
