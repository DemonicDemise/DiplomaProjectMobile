package com.example.diploma.providers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.braintreepayments.api.ClientTokenCallback;
import com.braintreepayments.api.ClientTokenProvider;
import com.google.logging.type.HttpRequest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class TokenProvider implements ClientTokenProvider {

    public static final String TAG = "TokenProvider";

    void getClientToken(@NonNull ClientTokenCallback callback) {
        Call<com.example.diploma.providers.ClientToken> call = createService().getClientToken();
        call.enqueue(new Callback<com.example.diploma.providers.ClientToken>() {
            @Override
            public void onResponse(Call<com.example.diploma.providers.ClientToken> call, Response<com.example.diploma.providers.ClientToken> response) {
                callback.onSuccess(response.body().getValue());
            }

            @Override
            public void onFailure(Call<ClientToken> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }
}
