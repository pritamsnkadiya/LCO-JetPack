package com.example.lcogetpack.ui.ui.activity.forumdetails;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.RequestModel;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.databinding.ActivityForumDetailsBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.adapter.ForumDetailsAnswerListAdapter;

import java.util.Observable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.hud;
import static com.example.lcogetpack.data.utils.Method.logInProgressDialogCall;

public class ForumDetailsViewModel extends Observable implements View.OnClickListener {

    public ActivityForumDetailsBinding binding;
    public Activity activity;
    public Context context;
    private String forum_id, role;
    private RecyclerView.Adapter mAnswerAdapter;
    @Inject
    Retrofit retrofit;

    public ForumDetailsViewModel(Context context, Activity activity, ActivityForumDetailsBinding binding, String forum_id, String role) {
        this.context = context;
        this.activity = activity;
        this.binding = binding;
        this.forum_id = forum_id;
        this.role = role;

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(ForumDetailsViewModel.this);

        binding.forumToolbarId.toolbarTitle.setText("Query Detail's");
        binding.forumToolbarId.imgSearch.setVisibility(View.GONE);

        binding.rvForumAnswerList.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvForumAnswerList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        binding.forumToolbarId.civBackBtn.setOnClickListener(this);
        binding.btSubmit.setOnClickListener(this);

        getAllForumsAnswer(forum_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_back_btn:
                activity.finish();
                break;

            case R.id.bt_submit:
                if (!binding.edAnswer.getText().toString().isEmpty()) {
                    submitForumAnswer();
                } else {
                    Toast.makeText(context, "Please Enter your answer and then Submit", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void getAllForumsAnswer(String forum_id) {
        logInProgressDialogCall(activity);

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllForumsAnswer(forum_id);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    binding.tvTitle.setText(response.body().result.Forum.title);
                    binding.tvBody.setText(response.body().result.Forum.body);
                    mAnswerAdapter = new ForumDetailsAnswerListAdapter(context, activity, response.body().result.answerList);
                    binding.rvForumAnswerList.setAdapter(mAnswerAdapter);

                    hud.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
                hud.dismiss();
            }
        });
    }

    private void submitForumAnswer() {
        RequestModel request = new RequestModel();
        request.forum_id = forum_id;
        request.answer = binding.edAnswer.getText().toString().trim();
        request.role = role;
        binding.edAnswer.setText("");

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.submitForumAnswer(request);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    getAllForumsAnswer(forum_id);
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
