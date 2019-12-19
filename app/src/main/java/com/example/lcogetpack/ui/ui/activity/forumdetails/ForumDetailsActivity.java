package com.example.lcogetpack.ui.ui.activity.forumdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.ActivityForumDetailsBinding;
import com.example.lcogetpack.ui.ui.activity.userprofile.ProfileActivity;

public class ForumDetailsActivity extends AppCompatActivity {

    private static String TAG = ProfileActivity.class.getName();
    public ActivityForumDetailsBinding binding;
    public ForumDetailsViewModel forumDetailsViewModel;
    public Activity activity;
    public Context context;
    public String forum_id, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;
        this.activity = ForumDetailsActivity.this;

        Intent intent = getIntent();
        if (intent != null) {
            forum_id = intent.getStringExtra("forum_id");
            role = intent.getStringExtra("role");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_forum_details);
        forumDetailsViewModel = new ForumDetailsViewModel(context, activity, binding, forum_id, role);
        binding.setForumViewModel(forumDetailsViewModel);
    }
}
