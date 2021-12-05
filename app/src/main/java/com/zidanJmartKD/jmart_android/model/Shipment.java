package com.zidanJmartKD.jmart_android.model;

public class Shipment {
    public static Plan INSTANT = new Plan((byte)(1));
    public static Plan SAME_DAY = new Plan((byte)(1 << 1));
    public static Plan NEXT_DAY = new Plan((byte)(1 << 2));
    public static Plan REGULER = new Plan((byte)(1 << 3));
    public static Plan KARGO = new Plan((byte)(1 << 4));
    public String address;
    public int cost;
    public byte plan;
    public String receipt;

    public static class Plan {
        public byte bit;

        private Plan(byte bit) {
            this.bit = bit;
        }
    }
}
