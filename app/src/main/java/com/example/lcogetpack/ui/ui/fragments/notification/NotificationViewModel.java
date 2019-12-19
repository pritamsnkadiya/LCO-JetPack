package com.example.lcogetpack.ui.ui.fragments.notification;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.databinding.FragmentNotificationBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.adapter.FriendsRequestListAdapter;
import com.example.lcogetpack.ui.ui.adapter.Notification_Other_Adapter;
import com.example.lcogetpack.ui.ui.presenter.PlansSelectInterface;

import java.util.Observable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.hud;
import static com.example.lcogetpack.data.utils.Method.logInProgressDialogCall;
import static com.example.lcogetpack.di.init.ApplicationAppContext.getAppContext;

public class NotificationViewModel extends Observable implements View.OnClickListener, FriendsRequestListAdapter.PostsAdapterListener {

    public Context context;
    public Activity activity;
    public FragmentNotificationBinding binding;
    public RecyclerView.Adapter mFriendRequestAdapter, mOtherNotificationAdapter;
    public FriendsRequestListAdapter.PostsAdapterListener listener;
    @Inject
    Retrofit retrofit;

    public NotificationViewModel(Context context, Activity activity, FragmentNotificationBinding binding) {
        this.context = context;
        this.activity = activity;
        this.binding = binding;
        listener = NotificationViewModel.this;

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(NotificationViewModel.this);

        binding.notificationToolbarId.imgSearch.setVisibility(View.GONE);
        binding.notificationToolbarId.toolbarTitle.setText("Notification's");
        binding.notificationToolbarId.civBackBtn.setOnClickListener(this);

        binding.rvFriendRequest.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvFriendRequest.setNestedScrollingEnabled(false);
        binding.rvFriendRequest.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        binding.rvNotificationOther.setNestedScrollingEnabled(false);
        binding.rvNotificationOther.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvNotificationOther.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        getFriendRequest();
    }

    @Override
    public void onClick(View v) {
        activity.finish();
    }

    public void getFriendRequest() {

        logInProgressDialogCall(activity);

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getFriendRequest();
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    mFriendRequestAdapter = new FriendsRequestListAdapter(context, activity, response.body().result.friendRequest, listener);
                    binding.rvFriendRequest.setAdapter(mFriendRequestAdapter);

                    mOtherNotificationAdapter = new Notification_Other_Adapter(context, activity, response.body().result.Broadcast);
                    binding.rvNotificationOther.setAdapter(mOtherNotificationAdapter);

                    hud.dismiss();
                } else {

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

    @Override
    public void onPostClicked(AllListData post, int type) {
        if (type == 0) {
            acceptFriendRequest(post.rowId);
        } else {
            declineFriendRequest(post.rowId);
        }
    }

    public void acceptFriendRequest(String rowId) {

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.acceptFriendRequest(rowId);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                Toast.makeText(context, "Accepted Request", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    public void declineFriendRequest(String rowId) {

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.declineFriendRequest(rowId);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                Toast.makeText(context, "Declined Request", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
