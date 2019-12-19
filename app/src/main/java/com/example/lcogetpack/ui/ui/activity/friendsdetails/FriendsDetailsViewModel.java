package com.example.lcogetpack.ui.ui.activity.friendsdetails;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.RequestModel;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.data.utils.Method;
import com.example.lcogetpack.databinding.ActivityFriendsDetailsBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.activity.conversation.ConversationActivity;
import com.example.lcogetpack.ui.ui.adapter.LcoChannelListAdapter;
import com.example.lcogetpack.ui.ui.adapter.MsoChannelListAdapter;

import java.util.ArrayList;
import java.util.Observable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FriendsDetailsViewModel extends Observable implements View.OnClickListener {

    public Activity activity;
    public Context context;
    private ActivityFriendsDetailsBinding binding;
    public RecyclerView.Adapter mAdapterChannelList, mAdapterPackageList;
    public ArrayList<AllListData> channelsList = new ArrayList<>();
    public ArrayList<AllListData> packagesList = new ArrayList<>();
    public String id, name, user_type;
    public String USER_ID, STATUS;
    public Dialog dialog;
    public RecyclerView rv_channel_list, rv_package_list;
    @Inject
    Retrofit retrofit;

    public FriendsDetailsViewModel(Context context, Activity activity, ActivityFriendsDetailsBinding binding, String user_id, String status) {
        this.binding = binding;
        this.activity = activity;
        this.context = context;
        this.USER_ID = user_id;
        this.STATUS = status;

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(FriendsDetailsViewModel.this);

        binding.friendToolbarId.imgSearch.setVisibility(View.GONE);
        binding.friendToolbarId.toolbarTitle.setText("User Profile");
        binding.friendToolbarId.civBackBtn.setOnClickListener(this);
        binding.llShowList.setOnClickListener(this);
        binding.fab.setOnClickListener(this);
        binding.tvStatus.setOnClickListener(this);

        if (STATUS.equalsIgnoreCase("Connected")) {
            binding.tvStatus.setText(STATUS);
            binding.tvStatus.setTextColor(Color.GREEN);
        } else if (STATUS.equalsIgnoreCase("Pending")) {
            binding.tvStatus.setText(STATUS);
            binding.tvStatus.setTextColor(Color.LTGRAY);
        } else {
            binding.tvStatus.setText(STATUS);
            binding.tvStatus.setTextColor(Color.RED);
        }
        getSearchUserDetails(USER_ID);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_show_list:
                showDialog();
                break;

            case R.id.civ_back_btn:
                activity.finish();
                break;

            case R.id.fab:
                if (STATUS.equalsIgnoreCase("Connected")) {
                    context.startActivity(new Intent(context, ConversationActivity.class).
                            putExtra("user_id", id).
                            putExtra("name", name).
                            putExtra("user_type", user_type));
                } else if (STATUS.equalsIgnoreCase("Pending")) {
                    Toast.makeText(context, "Please Add as a Network", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Please Add as a Network", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tv_status:
                if (STATUS.equalsIgnoreCase("Connected")) {
                    Toast.makeText(context, "This is all ready your Connected", Toast.LENGTH_SHORT).show();
                } else if (STATUS.equalsIgnoreCase("Pending")) {
                    Toast.makeText(context, "All ready in Pending", Toast.LENGTH_SHORT).show();
                } else {
                    sendFriendRequest();
                }
                break;
        }
    }

    public void getSearchUserDetails(String user_id) {
        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getSearchUserDetails(user_id);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().result.image == null) {
                        binding.imgView.setImageResource(R.mipmap.icon_user);
                    } else {
                        Glide.with(context)
                                .load("http://" + response.body().result.image.trim())
                                .into(binding.imgView);
                    }
                    id = response.body().result.id;
                    name = response.body().result.name;
                    if (response.body().result.is_mso.equalsIgnoreCase("1")) {
                        Method.getPreferences(context, "is_mso");
                        user_type = "mso";
                    }
                    if (response.body().result.is_lco.equalsIgnoreCase("1")) {
                        Method.getPreferences(context, "is_lco");
                        user_type = "lco";
                    }
                    if (response.body().result.is_isd.equalsIgnoreCase("1")) {
                        Method.getPreferences(context, "is_isd");
                        user_type = "isd";
                    }

                    binding.tvName.setText(response.body().result.name);
                    binding.tvPhone.setText(response.body().result.phone);
                    binding.tvEmail.setText(response.body().result.email);

                    if (response.body().result.address != null)
                        binding.tvAddress.setText(response.body().result.address);
                    binding.tvSubscription.setText(response.body().subscription);
                    binding.tvNoUsers.setText(response.body().user);

                    try {
                        if (response.body().result.channels.size() > 0) {
                            for (int i = 0; i < response.body().result.channels.size(); i++) {
                                channelsList.add(response.body().result.channels.get(i));
                            }
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    try {
                        if (response.body().result.packages.size() > 0) {
                            for (int i = 0; i < response.body().result.packages.size(); i++) {
                                packagesList.add(response.body().result.packages.get(i));
                            }
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    public void sendFriendRequest() {

        RequestModel request = new RequestModel();
        request.user_1_id = Method.getPreferences(context, "user_id");
        request.user_2_id = USER_ID;

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.sendFriendRequest(request);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    binding.tvStatus.setText("Pending");
                    binding.tvStatus.setTextColor(Color.LTGRAY);

                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    public void showDialog() {

        dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.layout_channels_packages);
        dialog.setTitle("List Of Your Channel's and Package's");
        dialog.setCanceledOnTouchOutside(true);

        rv_channel_list = dialog.findViewById(R.id.rv_channel_list);
        rv_package_list = dialog.findViewById(R.id.rv_package_list);

        rv_channel_list.addItemDecoration(new ItemOffsetDecoration(10));
        rv_channel_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mAdapterChannelList = new LcoChannelListAdapter(context, activity, channelsList);
        rv_channel_list.setAdapter(mAdapterChannelList);

        rv_package_list.addItemDecoration(new ItemOffsetDecoration(10));
        rv_package_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mAdapterPackageList = new MsoChannelListAdapter(context, activity, packagesList);
        rv_package_list.setAdapter(mAdapterPackageList);

        dialog.show();
    }
}
