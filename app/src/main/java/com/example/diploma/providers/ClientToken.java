package com.example.diploma.providers;

import com.braintreepayments.api.ClientTokenProvider;
import com.google.gson.annotations.SerializedName;



// In ClientToken.java file that you create
public class ClientToken {

    @SerializedName("")
    private String value;

    public String getValue() {
        return value;
    }
}
