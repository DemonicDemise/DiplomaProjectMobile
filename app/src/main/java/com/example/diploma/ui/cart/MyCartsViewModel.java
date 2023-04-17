package com.example.diploma.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyCartsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyCartsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is carts fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}