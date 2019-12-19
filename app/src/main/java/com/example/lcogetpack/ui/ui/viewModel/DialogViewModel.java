package com.example.lcogetpack.ui.ui.viewModel;

import android.app.Activity;
import android.content.Context;

import com.example.lcogetpack.databinding.LayoutSearchResultBinding;

import java.util.Observable;

public class DialogViewModel extends Observable {
    public Context context;
    public Activity activity;
    public LayoutSearchResultBinding binding;

    public DialogViewModel(Context context, Activity activity, LayoutSearchResultBinding binding) {
        this.activity = activity;
        this.context = context;
        this.binding = binding;
    }
}
