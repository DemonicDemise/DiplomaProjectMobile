package com.example.diploma.providers;

import okhttp3.OkHttpClient;

public class HttpRequest {

    private static final String TOKEN_ENDPOINT = "";
    private static final String CHECKOUT_ENDPOINT = "";

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public Api requestToken(){ return this.build(TOKEN_ENDPOINT).create(Api.class);}

    public static Api createService() {
        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();
        return retrofit.create(Api.class);
    }

    void getClientToken(@NonNull ClientTokenCallback callback) {
        Call<ClientToken> call = createService().getClientToken();
        call.enqueue(new Callback<ClientToken>() {
            @Override
            public void onResponse(Call<ClientToken> call, Response<ClientToken> response) {
                callback.onSuccess(response.body().getValue());
            }

            @Override
            public void onFailure(Call<ClientToken> call, Throwable t) {
                callback.onFailure(new Exception(t));
            }
        });
    }

}
