package com.example.lcogetpack.ui.ui.activity.friendsdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.ActivityFriendsDetailsBinding;

public class FriendsDetailsActivity extends AppCompatActivity {

    public Activity activity;
    public Context context;
    public ActivityFriendsDetailsBinding binding;
    public FriendsDetailsViewModel viewModel;
    public String USER_ID, STATUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = FriendsDetailsActivity.this;
        activity = this;

        Intent intent = getIntent();
        if (intent != null) {
            USER_ID = intent.getStringExtra("USER_ID");
            STATUS = intent.getStringExtra("STATUS");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_friends_details);
        viewModel = new FriendsDetailsViewModel(context, activity, binding, USER_ID, STATUS);
        binding.setFriendViewModel(viewModel);
    }
}
