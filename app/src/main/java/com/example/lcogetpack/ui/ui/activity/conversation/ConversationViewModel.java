package com.example.lcogetpack.ui.ui.activity.conversation;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.RequestModel;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.databinding.ActivityConversationBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.adapter.MultiViewTypeAdapter;

import java.util.Observable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.hud;

public class ConversationViewModel extends Observable implements View.OnClickListener {
    public Context context;
    public Activity activity;
    public ActivityConversationBinding binding;
    private RecyclerView.Adapter mMessageAdapter;
    public String user_id, user_type, name;
    @Inject
    Retrofit retrofit;

    public ConversationViewModel(Context context, Activity activity, ActivityConversationBinding binding, String user_id, String user_type, String name) {
        this.binding = binding;
        this.activity = activity;
        this.context = context;
        this.user_id = user_id;
        this.user_type = user_type;
        this.name = name;

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(ConversationViewModel.this);

        binding.rvMessageList.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvMessageList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        binding.idConversionToolbar.toolbarTitle.setText("Message " + name);

        binding.idConversionToolbar.civBackBtn.setOnClickListener(this);
        binding.imgBtnSend.setOnClickListener(this);
        binding.idConversionToolbar.llRefresh.setOnClickListener(this);

        getAllConversation(user_type, user_id);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_btn_send:
                if (!binding.edMessageText.toString().trim().isEmpty()) {
                    sendMessageTo();
                } else {
                    Toast.makeText(context, "Please Enter Something", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.civ_back_btn:
                activity.finish();
                break;

            case R.id.ll_refresh:
                YoYo.with(Techniques.BounceIn)
                        .duration(600)
                        .repeat(0)
                        .playOn(binding.idConversionToolbar.llRefresh);
                getAllConversation(user_type, user_id);
                break;
        }
    }

    public void getAllConversation(String user_type, String userId) {

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllConversation(user_type, userId);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {

                    mMessageAdapter = new MultiViewTypeAdapter(context, activity, response.body().result.msgs);
                    binding.rvMessageList.setAdapter(mMessageAdapter);
                    try {
                        binding.rvMessageList.smoothScrollToPosition(response.body().result.msgs.size() - 1);
                    } catch (Exception e) {
                        e.getMessage();
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

    public void sendMessageTo() {

        RequestModel request = new RequestModel();
        request.user_id = user_id;
        request.message = binding.edMessageText.getText().toString().trim();
        request.user_type = user_type;
        binding.edMessageText.setText("");

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.sendMessageTo(request);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    getAllConversation(user_type, user_id);
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
