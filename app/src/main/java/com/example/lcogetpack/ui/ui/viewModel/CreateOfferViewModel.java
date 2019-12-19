package com.example.lcogetpack.ui.ui.viewModel;

import android.app.Activity;
import android.content.Context;

import com.example.lcogetpack.databinding.LayoutCreateOfferBinding;

import java.util.Observable;

public class CreateOfferViewModel extends Observable {

    public Context context;
    public Activity activity;
    public LayoutCreateOfferBinding binding;

    public CreateOfferViewModel(Context context, Activity activity, LayoutCreateOfferBinding binding) {
        this.activity = activity;
        this.context = context;
        this.binding = binding;
    }
}
