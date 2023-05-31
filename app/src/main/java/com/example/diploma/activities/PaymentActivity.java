package com.example.diploma.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.DataCollector;
import com.braintreepayments.api.PayPalAccountNonce;
import com.braintreepayments.api.PayPalClient;
import com.braintreepayments.api.PayPalListener;
import com.example.diploma.R;
import com.google.firebase.database.core.TokenProvider;

public class PaymentActivity extends AppCompatActivity implements PayPalListener {

    private static final String TAG = "PaymentActivity";
    private static final String AMOUNT = "5";
    private static final String CURRENCY = "USD";

    private BraintreeClient braintreeClient;
    private PayPalClient payPalClient;
    private DataCollector dataCollector;

    private String payment_submitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        payment_submitted = intent.getStringExtra("payment_submitted");
        braintreeClient = new BraintreeClient(this, new TokenProvider());
        payPalClient = new PayPalClient(this, braintreeClient);
        payPalClient.setListener(this);

        dataCollector = new DataCollector(braintreeClient);
    }


    @Override
    public void onPayPalSuccess(@NonNull PayPalAccountNonce payPalAccountNonce) {

    }

    @Override
    public void onPayPalFailure(@NonNull Exception error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
