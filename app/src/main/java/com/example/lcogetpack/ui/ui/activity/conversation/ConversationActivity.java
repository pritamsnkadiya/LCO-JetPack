package com.example.lcogetpack.ui.ui.activity.conversation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.ActivityConversationBinding;

public class ConversationActivity extends AppCompatActivity {

    public Activity activity;
    public Context context;
    public ActivityConversationBinding binding;
    public ConversationViewModel conversationViewModel;
    public String user_id, user_type, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this;
        this.activity = ConversationActivity.this;

        Intent intent = getIntent();
        if (intent != null) {
            user_id = intent.getStringExtra("user_id");
            user_type = intent.getStringExtra("user_type");
            name = intent.getStringExtra("name");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_conversation);
        conversationViewModel = new ConversationViewModel(context, activity, binding, user_id,  user_type, name);
        binding.setConversationView(conversationViewModel);
    }
}
