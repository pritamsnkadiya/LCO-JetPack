package com.example.lcogetpack.ui.ui.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.ActivityMainBinding;
import com.example.lcogetpack.ui.ui.activity.login.LoginActivity;

import java.util.Observable;

public class MainViewModel extends Observable implements View.OnClickListener {

    private Context context;
    private Activity activity;
    private ActivityMainBinding binding;

    public MainViewModel(Context context, Activity activity, ActivityMainBinding binding) {

        this.binding = binding;
        this.activity = activity;
        this.context = context;

        callInitialization();
    }

    private void callInitialization() {

        binding.imgMso.setOnClickListener(this);
        binding.imgLco.setOnClickListener(this);
        binding.imgIsd.setOnClickListener(this);
        binding.imgLba.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_mso:
                context.startActivity(new Intent(context, LoginActivity.class).putExtra("KEY", "operators").putExtra("KEY2", "is_mso"));
                activity.finish();
                break;
            case R.id.img_lco:
                context.startActivity(new Intent(context, LoginActivity.class).putExtra("KEY", "operators").putExtra("KEY2", "is_lco"));
                activity.finish();
                break;
            case R.id.img_isd:
                context.startActivity(new Intent(context, LoginActivity.class).putExtra("KEY", "operators").putExtra("KEY2", "is_isd"));
                activity.finish();
                break;

            case R.id.img_lba:
                context.startActivity(new Intent(context, LoginActivity.class).putExtra("KEY", "users").putExtra("KEY2", "is_lba"));
                activity.finish();
                break;
        }
    }
}
