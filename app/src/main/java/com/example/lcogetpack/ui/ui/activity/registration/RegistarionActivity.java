package com.example.lcogetpack.ui.ui.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.ActivityRegistarionBinding;

public class RegistarionActivity extends AppCompatActivity {

    public Activity activity;
    public Context context;
    public ActivityRegistarionBinding binding;
    public RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;
        this.activity = RegistarionActivity.this;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registarion);
        viewModel = new RegistrationViewModel(context, activity, binding);
        binding.setRegistrationView(viewModel);
        binding.registrationId.toolbarTitle.setText("Register Here");
    }
}
