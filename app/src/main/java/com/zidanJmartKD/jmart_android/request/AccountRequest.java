package com.zidanJmartKD.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class AccountRequest extends RequestFactory {
    private static final String REGISTER_STORE = "/registerStore?name=%s&address=%s&phoneNumber=%s";
    private static final String TOP_UP="/topUp?balance=%f";
    private static final String parentURI = "account";

    public static StringRequest registerStore (
            String parentURI,
            int id,
            String name,
            String address,
            String phoneNumber,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ){
        String url = String.format(getURL(), parentURI, id);
        url = url.concat(REGISTER_STORE);
        url = String.format(url, name, address, phoneNumber);
        return new StringRequest(Request.Method.POST, url, listener, errorListener);
    }

    public static StringRequest topUp (
            int id,
            double balance,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ){
        String url = String.format(getURL(), parentURI, id);
        url = url.concat(TOP_UP);
        url = String.format(url, balance);
        return new StringRequest(Request.Method.POST, url, listener, errorListener);
    }
}
