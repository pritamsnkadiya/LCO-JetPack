package com.example.lcogetpack.ui.ui.fragments.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.Method;
import com.example.lcogetpack.databinding.FragmentSettingsBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.di.init.SessionManager;
import com.example.lcogetpack.ui.ui.activity.common.CommonActivity;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingViewModel implements View.OnClickListener {

    public static String TAG = SettingViewModel.class.getName();
    public Context context;
    public Activity activity;
    public SessionManager session;
    public FragmentSettingsBinding binding;
    public ResponsModel responsModel;
    @Inject
    Retrofit retrofit;

    public SettingViewModel(Context context, Activity activity, FragmentSettingsBinding binding) {
        this.activity = activity;
        this.context = context;
        this.binding = binding;

        session = new SessionManager(context);

        binding.llLogout.setOnClickListener(this);
        binding.llUpdateProfile.setOnClickListener(this);
        binding.llTraining.setOnClickListener(this);
        binding.llSubscription.setOnClickListener(this);
        binding.llAllNotification.setOnClickListener(this);
        binding.llFriends.setOnClickListener(this);
        binding.llPost.setOnClickListener(this);

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(SettingViewModel.this);

        getUserDetails();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_update_profile:
                // startActivity(new Intent(context, UpdateActivity.class).putExtra("Object", responsModel));
                Toast.makeText(context, "Under working ??", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_logout:
                logoutUser();
                session.logoutUser();
                activity.finish();
                break;

            case R.id.ll_all_notification:
                context.startActivity(new Intent(context, CommonActivity.class).putExtra("KEY", "1"));
                break;

            case R.id.ll_friends:
                context.startActivity(new Intent(context, CommonActivity.class).putExtra("KEY", "2"));
                break;

            case R.id.ll_post:
                context.startActivity(new Intent(context, CommonActivity.class).putExtra("KEY", "3"));
                break;

            case R.id.ll_training:
                context.startActivity(new Intent(context, CommonActivity.class).putExtra("KEY", "4"));
                break;
            case R.id.ll_subscription:
                context.startActivity(new Intent(context, CommonActivity.class).putExtra("KEY", "5"));
                break;
        }
    }

    public void getUserDetails() {
        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getUserDetail();
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    responsModel = response.body();
                    if (response.body().operator.image == null) {
                        binding.imgView.setImageResource(R.mipmap.icon_user);
                    } else {
                        Glide.with(context)
                                .load("http://" + response.body().operator.image.trim())
                                .into(binding.imgView);
                    }
                    binding.tvName.setText(response.body().operator.name);
                    binding.tvEmail.setText(response.body().operator.email);
                    binding.tvPhoneNo.setText(response.body().operator.phone);
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    public void logoutUser() {

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getLogoutUser();
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().result.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }

}
