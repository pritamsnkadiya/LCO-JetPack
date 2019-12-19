package com.example.lcogetpack.ui.ui.activity.sendcomment;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.RequestModel;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.databinding.ActivityConversationBinding;
import com.example.lcogetpack.databinding.ActivitySendCommentsBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.adapter.AllCommentsListAdapter;

import java.util.ArrayList;
import java.util.Observable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SendCommentsViewModel extends Observable implements View.OnClickListener {

    public Activity activity;
    public Context context;
    public ActivitySendCommentsBinding binding;
    public ArrayList<AllListData> modelArrayList = new ArrayList<>();
    public RecyclerView.Adapter mAdapterCommentsList;
    public String ID, DESCRIPTION, FILE, FILE_TYPE, COMMENTS, POST_ID, NAME, USER_IMAGE, is_lco, is_mso, is_isd, is_lba;
    @Inject
    Retrofit retrofit;

    public SendCommentsViewModel(Context context, Activity activity, ActivitySendCommentsBinding binding, String id, String description, String file, String file_type,
                                 String comments, String post_id, String name, String user_image, String is_lco, String is_mso, String is_isd, String is_lba) {
        this.context = context;
        this.activity = activity;
        this.binding = binding;
        this.ID = id;
        this.DESCRIPTION = description;
        this.FILE = file;
        this.FILE_TYPE = file_type;
        this.COMMENTS = comments;
        this.POST_ID = post_id;
        this.NAME = name;
        this.USER_IMAGE = user_image;
        this.is_lco = is_lco;
        this.is_mso = is_mso;
        this.is_isd = is_isd;
        this.is_lba = is_lba;

        binding.sendCommentsId.imgSearch.setVisibility(View.GONE);
        binding.sendCommentsId.toolbarTitle.setText("Send Comment's");
        binding.rvCommentsList.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvCommentsList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        binding.sendCommentsId.civBackBtn.setOnClickListener(this);
        binding.btSend.setOnClickListener(this);

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(SendCommentsViewModel.this);

        getAllComments(ID);

        if (USER_IMAGE == null) {
            binding.profilePic.setImageResource(R.mipmap.icon_user);
        } else {
            Glide.with(context)
                    .load("http://" + USER_IMAGE)
                    .into(binding.profilePic);
        }

        binding.tvName.setText(NAME);
        binding.tvRole.setText(is_mso + " " + is_lco + " " + is_isd + " " + is_lba);
        binding.tvDescription.setText(DESCRIPTION);

        if (FILE_TYPE.equalsIgnoreCase("0")) {
            if (FILE == null) {
                binding.imgPostImage.setImageResource(R.mipmap.icon_user);
            } else {
                Glide.with(context)
                        .load("http://" + FILE)
                        .into(binding.imgPostImage);
            }

        } else if (FILE_TYPE.equalsIgnoreCase("1")) {
            binding.postVideo.setVideoPath("http://" + FILE);
            binding.postVideo.setMediaController(new MediaController(context));
            binding.postVideo.requestFocus();
            binding.postVideo.start();
        } else {

        }

        if (FILE_TYPE.equalsIgnoreCase("0")) {
            binding.imgPostImage.setVisibility(View.VISIBLE);
        } else if (FILE_TYPE.equalsIgnoreCase("1")) {
            binding.videoFrame.setVisibility(View.VISIBLE);
        } else {
            binding.videoFrame.setVisibility(View.GONE);
            binding.imgPostImage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send:
                if (!binding.edComments.getText().toString().isEmpty()) {
                    sendComments(ID, binding.edComments.getText().toString().trim());
                } else {
                    Toast.makeText(context, "Please write your comment's in Box", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.civ_back_btn:
                activity.finish();
                break;
        }
    }

    public void getAllComments(String id) {
        modelArrayList.clear();
        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllComments(id);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().result.reactionDetails.size(); i++)
                        modelArrayList.add(response.body().result.reactionDetails.get(i));

                    if (!modelArrayList.isEmpty()) {
                        binding.llAllHeader.setVisibility(View.VISIBLE);
                        mAdapterCommentsList = new AllCommentsListAdapter(context, activity, modelArrayList);
                        binding.rvCommentsList.setAdapter(mAdapterCommentsList);

                    } else {
                        Toast.makeText(context, "No data available ?", Toast.LENGTH_SHORT);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    private void sendComments(String post_id, String comment) {

        RequestModel request = new RequestModel();
        request.comment = comment;
        request.post_id = post_id;

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.sendComments(request);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Your comment sent", Toast.LENGTH_SHORT).show();
                    activity.finish();
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
