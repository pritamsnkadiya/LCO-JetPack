package com.example.lcogetpack.ui.ui.activity.registration;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.lcogetpack.databinding.ActivityRegistarionBinding;

import java.util.Observable;

public class RegistrationViewModel extends Observable {

    public Activity activity;
    public Context context;
    public ActivityRegistarionBinding binding;

    public RegistrationViewModel(Context context, Activity activity, ActivityRegistarionBinding binding) {
        this.context = context;
        this.activity = activity;
        this.binding = binding;

        binding.registrationId.imgSearch.setVisibility(View.GONE);
    }
}
