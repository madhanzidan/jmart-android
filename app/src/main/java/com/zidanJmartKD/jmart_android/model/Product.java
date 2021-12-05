package com.zidanJmartKD.jmart_android.model;

public class Product extends Serializable {
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    public String toString(){
        /*
        return
                "accountId " + accountId +
                        "\nName: "+ name +
                        "\nWeight: " + weight +
                        "\nconditionUsed: " + conditionUsed +
                        "\nprice: " + price +
                        "\ndiscount " + discount +
                        "\ncategory: " + category +
                        "\nshipmentPlans: " + shipmentPlans;*/
      return name;
    }
}
