package com.example.lcogetpack.ui.ui.fragments.message;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.databinding.FragmentMessageBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.adapter.AllMessagesListAdapter;
import com.example.lcogetpack.ui.ui.adapter.AllUsersListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.hud;
import static com.example.lcogetpack.data.utils.Method.logInProgressDialogCall;

public class MessageViewModel extends ViewModel {

    public static String TAG = MessageViewModel.class.getName();
    public Context context;
    public Activity activity;
    private FragmentMessageBinding binding;
    public RecyclerView.Adapter mMessageAdapter;
    private List<AllListData> userList = new ArrayList<>();
    @Inject
    Retrofit retrofit;

    public MessageViewModel(Context context, Activity activity, FragmentMessageBinding binding) {
        this.activity = activity;
        this.context = context;
        this.binding = binding;

        binding.toolbarMenuId.imgSearch.setVisibility(View.GONE);
        binding.toolbarMenuId.civBackBtn.setVisibility(View.GONE);
        binding.toolbarMenuId.toolbarTitle.setText("Message List");
        binding.rvAllMessageList.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvAllMessageList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(MessageViewModel.this);

        getAllMessagesList();
    }

    public void getAllMessagesList() {

        logInProgressDialogCall(activity);
        userList.clear();

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllMessagesList();
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().result.userlist.size(); i++) {
                        userList.add(response.body().result.userlist.get(i));
                    }
                    if (userList.isEmpty()) {
                        binding.llCard2.setVisibility(View.VISIBLE);
                        binding.rvAllMessageList.setVisibility(View.GONE);
                        YoYo.with(Techniques.ZoomInUp)
                                .duration(2700)
                                .repeat(0)
                                .playOn(binding.llCard2);
                        mMessageAdapter = new AllMessagesListAdapter(context, activity, userList);
                        binding.rvAllMessageList.setAdapter(mMessageAdapter);
                        hud.dismiss();
                    } else {
                        binding.rvAllMessageList.setVisibility(View.VISIBLE);
                        binding.llCard2.setVisibility(View.GONE);
                        mMessageAdapter = new AllMessagesListAdapter(context, activity, userList);
                        binding.rvAllMessageList.setAdapter(mMessageAdapter);
                        hud.dismiss();
                    }

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
}