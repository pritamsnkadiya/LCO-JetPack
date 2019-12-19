package com.example.lcogetpack.ui.ui.fragments.offer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.RequestModel;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.databinding.FragmentOfferBinding;
import com.example.lcogetpack.databinding.LayoutCreateOfferBinding;
import com.example.lcogetpack.di.api.ApiClient;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.adapter.AllOffersListAdapter;
import com.example.lcogetpack.ui.ui.viewModel.CreateOfferViewModel;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.alertDialog;
import static com.example.lcogetpack.data.utils.Method.hud;
import static com.example.lcogetpack.data.utils.Method.logInProgressDialogCall;

public class OffersViewModel extends ViewModel{

    public static String TAG = OffersViewModel.class.getName();
    public Context context;
    public Activity activity;
    public FragmentOfferBinding binding;
    public RecyclerView.Adapter mOfferAdapter;
    @Inject
    Retrofit retrofit;

    public OffersViewModel(Context context, Activity activity, FragmentOfferBinding binding) {
        this.activity = activity;
        this.context = context;
        this.binding = binding;

        binding.headerToolbarId.imgSearch.setVisibility(View.GONE);
        binding.headerToolbarId.civBackBtn.setVisibility(View.GONE);
        binding.headerToolbarId.toolbarTitle.setText("Offer List");

        binding.rvOfferList.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvOfferList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(OffersViewModel.this);

        binding.fab.setOnClickListener(v -> callCreateOfferDialog());

        getAllOffers();
    }

    public void getAllOffers() {

        logInProgressDialogCall(activity);

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllOffers();
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    mOfferAdapter = new AllOffersListAdapter(context, activity, response.body().result.offerList);
                    binding.rvOfferList.setAdapter(mOfferAdapter);
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

    private void callCreateOfferDialog() {

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
            requestModel.detail = binding.edBody.getText().toString().trim();

            ApiInterface mService = retrofit.create(ApiInterface.class);
            Call<ResponsModel> responseModelCall = mService.createOffer(requestModel);
            responseModelCall.enqueue(new Callback<ResponsModel>() {
                @Override
                public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                    if (response.isSuccessful()) {
                        alertDialog.dismiss();
                        Toast.makeText(context, response.body().message, Toast.LENGTH_SHORT).show();
                        getAllOffers();
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