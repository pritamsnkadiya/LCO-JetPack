package com.example.lcogetpack.ui.ui.fragments.post;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.RequestModel;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.databinding.FragmentPostBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.activity.createpost.CreatePostActivity;
import com.example.lcogetpack.ui.ui.adapter.FriendsRequestListAdapter;
import com.example.lcogetpack.ui.ui.adapter.Posts_Adapter;

import java.util.Observable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.hud;
import static com.example.lcogetpack.data.utils.Method.logInProgressDialogCall;

public class PostViewModel extends Observable implements View.OnClickListener, Posts_Adapter.PostsAdapterListener {

    public Context context;
    public Activity activity;
    public FragmentPostBinding binding;
    public RecyclerView.Adapter mAdapterUserPostList;
    public Posts_Adapter.PostsAdapterListener listener;
    @Inject
    Retrofit retrofit;

    public PostViewModel(Context context, Activity activity, FragmentPostBinding binding) {
        this.context = context;
        this.activity = activity;
        this.binding = binding;
        listener = PostViewModel.this;

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(PostViewModel.this);

        binding.postToolbarId.imgSearch.setVisibility(View.GONE);
        binding.postToolbarId.toolbarTitle.setText("Post's");
        binding.fab.setOnClickListener(this);
        binding.postToolbarId.civBackBtn.setOnClickListener(this);

        binding.rvPost.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvPost.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        getAllPosts();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                context.startActivity(new Intent(context, CreatePostActivity.class));
                break;

            case R.id.civ_back_btn:
                activity.finish();
                break;
        }
    }

    public void getAllPosts() {

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllPosts();
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    mAdapterUserPostList = new Posts_Adapter(context, activity, response.body().result.posts, listener);
                    binding.rvPost.setAdapter(mAdapterUserPostList);
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    @Override
    public void onPostClicked(AllListData post, int type) {
        if (type == 1) {
            sendLikesRequest(post.id, "1");
        } else if (type == 0) {
            sendLikesRequest(post.id, "0");
        } else {
        }
    }

    private void sendLikesRequest(String post_id, String like_type) {

        RequestModel request = new RequestModel();
        request.like = like_type;
        request.post_id = post_id;

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.sendLikesRequest(request);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "You Liked", Toast.LENGTH_SHORT).show();
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
