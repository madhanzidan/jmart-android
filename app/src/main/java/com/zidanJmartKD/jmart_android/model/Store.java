package com.zidanJmartKD.jmart_android.model;

public class Store {
    public static final String REGEX_PHONE = "^[0-9]{9,12}\b";
    public static final String REGEX_NAME = "^[A-Z][a-z\\sa-z]{4,19}\b";
    public String address;
    public double balance;
    public String name;
    public String phoneNumber;

}
