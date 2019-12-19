package com.example.lcogetpack.ui.ui.fragments.friends;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.databinding.FragmentFriendBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.adapter.FriendListAdapter;
import com.example.lcogetpack.ui.ui.adapter.FriendsSearchListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.hud;
import static com.example.lcogetpack.data.utils.Method.logInProgressDialogCall;

public class FriendsViewModel extends Observable implements View.OnClickListener, TextWatcher {

    public Context context;
    public Activity activity;
    public FragmentFriendBinding binding;
    public RecyclerView.Adapter mAdapterUserList, mAdapterSearchUserList;
    private List<AllListData> userList = new ArrayList<>();
    private List<AllListData> searchUserList = new ArrayList<>();
    @Inject
    Retrofit retrofit;

    public FriendsViewModel(Context context, Activity activity, FragmentFriendBinding binding) {
        this.activity = activity;
        this.context = context;
        this.binding = binding;

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(FriendsViewModel.this);

        binding.mainToolbarId.toolbarTitle.setText("Friend's");
        binding.mainToolbarId.imgSearch.setVisibility(View.VISIBLE);

        binding.rvUserList.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvUserList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        binding.rvSearchUserList.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvSearchUserList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        binding.mainToolbarId.civBackBtn.setOnClickListener(this);
        binding.searchToolbarId.civBackSearch.setOnClickListener(this);
        binding.mainToolbarId.imgSearch.setOnClickListener(this);

        binding.searchToolbarId.edSearch.addTextChangedListener(this);

        getAllFriends();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_back_btn:
                activity.finish();
            case R.id.civ_back_search:
                binding.llMain.setVisibility(View.VISIBLE);
                binding.llSearch.setVisibility(View.GONE);
                break;

            case R.id.img_search:
                binding.llMain.setVisibility(View.GONE);
                binding.llSearch.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() != 0) {
            getAllSearchedFriends(String.valueOf(s));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void getAllFriends() {

        logInProgressDialogCall(activity);
        userList.clear();

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllFriends();
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().result.userList.size(); i++) {
                        userList.add(response.body().result.userList.get(i));
                    }

                    if (userList.isEmpty()) {
                        binding.llCard2.setVisibility(View.VISIBLE);
                        binding.llCard.setVisibility(View.GONE);
                        YoYo.with(Techniques.ZoomInUp)
                                .duration(2700)
                                .repeat(0)
                                .playOn(binding.llCard2);
                        mAdapterUserList = new FriendListAdapter(context, activity, userList);
                        binding.rvUserList.setAdapter(mAdapterUserList);
                        hud.dismiss();
                    } else {
                        binding.llCard.setVisibility(View.VISIBLE);
                        binding.llCard2.setVisibility(View.GONE);
                        mAdapterUserList = new FriendListAdapter(context, activity, userList);
                        binding.rvUserList.setAdapter(mAdapterUserList);
                        hud.dismiss();
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
                hud.dismiss();
            }
        });
    }

    public void getAllSearchedFriends(String query) {

        searchUserList.clear();

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllSearchedFriends(query);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().result.userList.size(); i++) {
                        searchUserList.add(response.body().result.userList.get(i));
                    }

                    if (!searchUserList.isEmpty()) {
                        mAdapterSearchUserList = new FriendsSearchListAdapter(context, activity, searchUserList);
                        binding.rvSearchUserList.setAdapter(mAdapterSearchUserList);
                    } else {
                        mAdapterSearchUserList = new FriendsSearchListAdapter(context, activity, searchUserList);
                        binding.rvSearchUserList.setAdapter(mAdapterSearchUserList);
                        Toast.makeText(context, "User's not Available", Toast.LENGTH_SHORT).show();
                    }

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
