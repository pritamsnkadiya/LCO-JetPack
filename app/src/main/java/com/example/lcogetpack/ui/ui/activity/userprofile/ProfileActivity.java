package com.example.lcogetpack.ui.ui.activity.userprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.ActivityDashboardBinding;

public class ProfileActivity extends AppCompatActivity {

    private static String TAG = ProfileActivity.class.getName();
    public ActivityDashboardBinding binding;
    public ProfileViewModel profileViewModel;
    public Activity activity;
    public Context context;
    public String user_id, is_mso, is_lco, is_isd, is_lba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;
        this.activity = ProfileActivity.this;

        Intent intent = getIntent();
        if (intent != null) {
            user_id = intent.getStringExtra("user_id");
            is_mso = intent.getStringExtra("is_mso");
            is_lco = intent.getStringExtra("is_lco");
            is_isd = intent.getStringExtra("is_isd");
            is_lba = intent.getStringExtra("is_lba");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        profileViewModel = new ProfileViewModel(context, activity, binding, user_id, is_mso, is_lco, is_isd, is_lba);
        binding.setDashboardView(profileViewModel);
    }
}
