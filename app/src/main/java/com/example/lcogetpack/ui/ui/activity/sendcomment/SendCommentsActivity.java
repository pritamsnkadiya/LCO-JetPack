package com.example.lcogetpack.ui.ui.activity.sendcomment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.ActivitySendCommentsBinding;

public class SendCommentsActivity extends AppCompatActivity {

    public ActivitySendCommentsBinding binding;
    public SendCommentsViewModel commentsViewModel;
    public Activity activity;
    public Context context;
    public String ID, DESCRIPTION, FILE, FILE_TYPE, COMMENTS, POST_ID, NAME, USER_IMAGE, is_lco, is_mso, is_isd, is_lba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        activity = SendCommentsActivity.this;

        Intent intent = getIntent();
        if (intent != null) {
            ID = intent.getStringExtra("id");
            DESCRIPTION = intent.getStringExtra("description");
            FILE = intent.getStringExtra("file");
            FILE_TYPE = intent.getStringExtra("file_type");
            COMMENTS = intent.getStringExtra("comment");
            POST_ID = intent.getStringExtra("post_id");
            NAME = intent.getStringExtra("name");
            USER_IMAGE = intent.getStringExtra("user_image");
            is_lco = intent.getStringExtra("is_lco");
            is_mso = intent.getStringExtra("is_mso");
            is_isd = intent.getStringExtra("is_isd");
            is_lba = intent.getStringExtra("is_lba");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_comments);
        commentsViewModel = new SendCommentsViewModel(context, activity, binding, ID, DESCRIPTION, FILE, FILE_TYPE, COMMENTS, POST_ID, NAME, USER_IMAGE, is_lco, is_mso, is_isd, is_lba);
        binding.setCommentsView(commentsViewModel);
    }
}
