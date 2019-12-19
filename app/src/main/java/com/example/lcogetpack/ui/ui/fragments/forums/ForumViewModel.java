package com.example.lcogetpack.ui.ui.fragments.forums;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.RequestModel;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.databinding.FragmentForumsBinding;
import com.example.lcogetpack.databinding.LayoutCreateOfferBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.adapter.AllMessagesListAdapter;
import com.example.lcogetpack.ui.ui.adapter.ForumListAdapter;
import com.example.lcogetpack.ui.ui.viewModel.CreateOfferViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.alertDialog;
import static com.example.lcogetpack.data.utils.Method.hud;
import static com.example.lcogetpack.data.utils.Method.logInProgressDialogCall;

public class ForumViewModel implements View.OnClickListener, TextWatcher {

    public static String TAG = ForumViewModel.class.getName();
    public Context context;
    public Activity activity;
    public FragmentForumsBinding binding;
    public ForumListAdapter mAdapterUserList;
    private List<AllListData> userList = new ArrayList<>();
    @Inject
    Retrofit retrofit;

    public ForumViewModel(Context context, Activity activity, FragmentForumsBinding binding) {
        this.activity = activity;
        this.context = context;
        this.binding = binding;

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(ForumViewModel.this);

        binding.forumToolbarId.civBackBtn.setVisibility(View.GONE);
        binding.forumToolbarId.toolbarTitle.setText("Forum");

        binding.rvForumList.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvForumList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        binding.searchToolbarId.civBackSearch.setOnClickListener(this);
        binding.forumToolbarId.imgSearch.setOnClickListener(this);
        binding.llSearch.setOnClickListener(this);

        binding.searchToolbarId.edSearch.setHint("Search forum's");

        binding.searchToolbarId.edSearch.addTextChangedListener(this);
        binding.fab.setOnClickListener(this);
        binding.llMain.setVisibility(View.VISIBLE);
        getAllForums();
    }

    public void getAllForums() {

        logInProgressDialogCall(activity);
        userList.clear();

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllForums();
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().result.forumList.size(); i++)
                        userList.add(response.body().result.forumList.get(i));

                    mAdapterUserList = new ForumListAdapter(context, activity, userList);
                    binding.rvForumList.setAdapter(mAdapterUserList);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                callCreateForumDialog();
                break;

            case R.id.civ_back_search:
                binding.llMain.setVisibility(View.VISIBLE);
                binding.llSearch.setVisibility(View.GONE);
                binding.searchToolbarId.edSearch.setText("");
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
    public void onTextChanged(CharSequence editable, int start, int before, int count) {
        if (editable.length() != 0) {
            filter(editable.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<AllListData> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (AllListData s : userList) {
            //if the existing elements contains the search input
            if (s.title.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        try {
            mAdapterUserList.filterList(filterdNames);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void callCreateForumDialog() {

        LayoutCreateOfferBinding binding;
        CreateOfferViewModel createOfferViewModel;
        View view;

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_create_offer, null, false);
        view = binding.getRoot();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        alertDialog = builder.create();
        createOfferViewModel = new CreateOfferViewModel(context, activity, binding);
        binding.setCreateOfferView(createOfferViewModel);

        binding.dialogToolbarId.imgSearch.setVisibility(View.GONE);
        binding.dialogToolbarId.civBackBtn.setOnClickListener(v -> alertDialog.dismiss());
        binding.dialogToolbarId.toolbarTitle.setText("Create Offer");

        binding.btProceed.setOnClickListener(v -> {

            RequestModel requestModel = new RequestModel();
            requestModel.title = binding.edTitle.getText().toString().trim();
            requestModel.body = binding.edBody.getText().toString().trim();

            ApiInterface mService = retrofit.create(ApiInterface.class);
            Call<ResponsModel> responseModelCall = mService.createForum(requestModel);
            responseModelCall.enqueue(new Callback<ResponsModel>() {
                @Override
                public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                    if (response.isSuccessful()) {
                        alertDialog.dismiss();
                        Toast.makeText(context, response.body().message, Toast.LENGTH_SHORT).show();
                        getAllForums();
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ResponsModel> call, Throwable t) {
                    t.getMessage();
                }
            });
        });

        alertDialog.show();
    }
}
