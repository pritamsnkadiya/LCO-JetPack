package com.example.lcogetpack.ui.ui.activity.createpost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.utils.Method;
import com.example.lcogetpack.databinding.ActivityCreatePostBinding;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.fragments.post.PostViewModel;

import java.util.Observable;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class CreatePostViewModel extends Observable implements View.OnClickListener {

    public static String TAG = CreatePostViewModel.class.getName();
    public Activity activity;
    public Context context;
    public ActivityCreatePostBinding binding;
    private static final String VIDEO_DIRECTORY = "/ifco";
    public int GALLERY = 11, CAMERA = 22;
    public int TYPE = 2;
    public String media_path = "";
    public static final int TAKE_PICTURE = 1;
    public String ImageName;
    private Boolean profileImage = false;
    public String imagePath = null;
    public String image_uri;
    public String image_name;
    public String image_format;
    @Inject
    Retrofit retrofit;

    public CreatePostViewModel(Context context, Activity activity, ActivityCreatePostBinding binding) {
        this.context = context;
        this.activity = activity;
        this.binding = binding;

      //  ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(CreatePostViewModel.this);

        binding.createPostId.imgSearch.setVisibility(View.GONE);
        binding.createPostId.toolbarTitle.setText("Create Post");

        binding.imgChooseImage.setOnClickListener(this);
        binding.imgChooseVideo.setOnClickListener(this);
        binding.tvCreatePost.setOnClickListener(this);
        binding.createPostId.civBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
