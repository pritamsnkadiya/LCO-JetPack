package com.example.lcogetpack.ui.ui.fragments.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.RequestModel;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.data.utils.Method;
import com.example.lcogetpack.databinding.FragmentHomeBinding;
import com.example.lcogetpack.databinding.LayoutSearchResultBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.adapter.AllUsersListAdapter;
import com.example.lcogetpack.ui.ui.adapter.AllUsersListSearchAdapter;
import com.example.lcogetpack.ui.ui.viewModel.DialogViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.alertDialog;
import static com.example.lcogetpack.data.utils.Method.hud;
import static com.example.lcogetpack.data.utils.Method.logInProgressDialogCall;

public class HomeViewModel extends ViewModel implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public Context context;
    public Activity activity;
    public FragmentHomeBinding binding;
    public String type;
    private RecyclerView.Adapter mAdapterUserList;
    private List<AllListData> userList = new ArrayList<>();
    private String stateID = "", cityId = "";
    private ArrayList<AllListData> stateList = new ArrayList<>();
    private ArrayList<AllListData> cityList = new ArrayList<>();
    public Dialog dialog;
    private List<AllListData> searchUserList = new ArrayList<>();
    @Inject
    Retrofit retrofit;

    public HomeViewModel(Context context, Activity activity, FragmentHomeBinding binding) {
        this.activity = activity;
        this.context = context;
        this.binding = binding;

        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(HomeViewModel.this);

        setUpViews();

        binding.spState.setOnItemSelectedListener(this);
        binding.spCity.setOnItemSelectedListener(this);

        binding.fab.setOnClickListener(this);
        binding.toolbarMenuId.civBackBtn.setVisibility(View.GONE);
        binding.toolbarMenuId.OnCreateOptionMenu.setOnClickListener(this);
        binding.btSearch.setOnClickListener(this);

        getAllState();

    }

    private void setUpViews() {

        binding.rvUserList.addItemDecoration(new ItemOffsetDecoration(10));
        binding.rvUserList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        if (Method.getPreferences(context, "is_mso").equalsIgnoreCase("1")) {
            binding.toolbarMenuId.toolbarTitle.setText("MSO DASHBOARD");
            binding.tvNameUnder.setText("List of MSO's");
            getAllList("mso");
        } else if (Method.getPreferences(context, "is_lco").equalsIgnoreCase("1")) {
            binding.toolbarMenuId.toolbarTitle.setText("LCO DASHBOARD");
            binding.tvNameUnder.setText("List of LCO's");
            getAllList("lco");
        } else if (Method.getPreferences(context, "is_isd").equalsIgnoreCase("1")) {
            binding.toolbarMenuId.toolbarTitle.setText("ISD DASHBOARD");
            binding.tvNameUnder.setText("List of ISD's");
            getAllList("isd");
        } else if (Method.getPreferences(context, "is_lba").equalsIgnoreCase("1")) {
            binding.toolbarMenuId.toolbarTitle.setText("LBA DASHBOARD");
            binding.tvNameUnder.setText("List of LBA's");
            getAllList("lba");
        } else {
            Toast.makeText(context, "No user available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
      /*      case R.id.civ_back_btn:
                activity.finish();
                break;*/
            case R.id.OnCreate_optionMenu:
                showDialog();
                break;

            case R.id.bt_search:
                if (!stateID.isEmpty()) {
                    getSearchData();
                } else {
                    Toast.makeText(context, "Please choose State", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void showDialog() {

        dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.layout_dailog_list);
        dialog.setTitle("Select Type of user");
        dialog.setCanceledOnTouchOutside(true);

        if (Method.getPreferences(context, "is_mso").equalsIgnoreCase("1")) {
            dialog.findViewById(R.id.img_lco_list).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.img_mso_list).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.img_isd_list).setVisibility(View.VISIBLE);
        }
        if (Method.getPreferences(context, "is_lco").equalsIgnoreCase("1")) {
            dialog.findViewById(R.id.img_lco_list).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.img_user_list).setVisibility(View.VISIBLE);
        }
        if (Method.getPreferences(context, "is_isd").equalsIgnoreCase("1")) {
            dialog.findViewById(R.id.img_lba_list).setVisibility(View.VISIBLE);
        }
        if (Method.getPreferences(context, "is_lba").equalsIgnoreCase("1")) {
            dialog.findViewById(R.id.img_user_list).setVisibility(View.VISIBLE);
        }

        dialog.findViewById(R.id.img_mso_list).setOnClickListener(view -> {
            getAllList("mso");
            binding.tvNameUnder.setText("List of MSO's");
            dialog.dismiss();
        });

        dialog.findViewById(R.id.img_lco_list).setOnClickListener(view -> {
            binding.tvNameUnder.setText("List of LCO's");
            getAllList("lco");
            dialog.dismiss();
        });

        dialog.findViewById(R.id.img_user_list).setOnClickListener(view -> {
            if (Method.getPreferences(context, "is_lba").equalsIgnoreCase("1")) {
                binding.tvNameUnder.setText("List of LBA's");
                getAllLbaList("lbauser");
                dialog.dismiss();
            } else {
                binding.tvNameUnder.setText("List of LCO's");
                getAllList("user");
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.img_lba_list).setOnClickListener(view -> {
            binding.toolbarMenuId.toolbarTitle.setText("LBA Dashboard");
            getAllList("lba");
            dialog.dismiss();
        });

        dialog.findViewById(R.id.img_isd_list).setOnClickListener(view -> {
            binding.tvNameUnder.setText("List of ISD's");
            getAllList("isd");
            dialog.dismiss();
        });

        dialog.show();
    }

    public void getAllList(String user_type) {
        logInProgressDialogCall(activity);

        type = user_type;
        userList.clear();

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllList(user_type);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().result.userList.size() > 0) {
                        for (int i = 0; i < response.body().result.userList.size(); i++) {
                            userList.add(response.body().result.userList.get(i));
                        }
                        binding.llCard.setVisibility(View.VISIBLE);
                        binding.llCard2.setVisibility(View.GONE);
                        mAdapterUserList = new AllUsersListAdapter(context, activity, userList, type);
                        binding.rvUserList.setAdapter(mAdapterUserList);
                        hud.dismiss();
                    } else {
                        binding.llCard.setVisibility(View.VISIBLE);
                        binding.llCard2.setVisibility(View.GONE);
                        YoYo.with(Techniques.ZoomInUp)
                                .duration(2700)
                                .repeat(0)
                                .playOn(binding.llCard2);
                        mAdapterUserList = new AllUsersListAdapter(context, activity, userList, type);
                        binding.rvUserList.setAdapter(mAdapterUserList);
                        hud.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
                hud.dismiss();
            }
        });
    }

    public void getAllLbaList(String user_type) {
        logInProgressDialogCall(activity);
        type = user_type;
        userList.clear();

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllLbaList(user_type);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {

                if (response.isSuccessful()) {
                    if (response.body().result.userList.size() > 0) {
                        for (int i = 0; i < response.body().result.userList.size(); i++) {
                            userList.add(response.body().result.userList.get(i));
                        }
                        binding.llCard.setVisibility(View.VISIBLE);
                        binding.llCard2.setVisibility(View.GONE);
                        binding.fab.setVisibility(View.VISIBLE);

                        mAdapterUserList = new AllUsersListAdapter(context, activity, userList, type);
                        binding.rvUserList.setAdapter(mAdapterUserList);
                    } else {
                        mAdapterUserList = new AllUsersListAdapter(context, activity, userList, type);
                        binding.rvUserList.setAdapter(mAdapterUserList);
                        binding.llCard2.setVisibility(View.VISIBLE);
                        binding.llCard.setVisibility(View.GONE);
                        binding.fab.setVisibility(View.GONE);

                        YoYo.with(Techniques.ZoomInUp)
                                .duration(2700)
                                .repeat(0)
                                .playOn(binding.llCard2);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
                hud.dismiss();
            }
        });
    }

    public void getAllState() {

        stateList.clear();

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllState();
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().result.stateList.size(); i++)
                        stateList.add(response.body().result.stateList.get(i));
                    populateStateSpinner();
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    private void populateStateSpinner() {
        List<String> lables = new ArrayList<>();
        lables.add("Select State");
        for (int i = 0; i < stateList.size(); i++) {
            lables.add(stateList.get(i).state_name);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spState.setAdapter(spinnerAdapter);
    }

    public void getAllCity(String user_id) {

        cityList.clear();
        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getAllCity(user_id);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().result.cityList.size(); i++)
                        cityList.add(response.body().result.cityList.get(i));
                    populateCitySpinner();
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    private void populateCitySpinner() {
        List<String> lables = new ArrayList<>();
        lables.add("Select City");
        for (int i = 0; i < cityList.size(); i++) {
            lables.add(cityList.get(i).city_name);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, lables);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spCity.setAdapter(spinnerAdapter);
    }

    public void getSearchData() {
        logInProgressDialogCall(activity);
        searchUserList.clear();
        RequestModel request = new RequestModel();
        request.user_type = type;
        request.state = stateID;
        request.city = cityId;

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.getSearchData(request);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().result.userList.size(); i++) {
                        searchUserList.add(response.body().result.userList.get(i));
                    }
                    if (!searchUserList.isEmpty()) {
                        callSearchResult();
                        hud.dismiss();
                    } else {
                        Toast.makeText(context, "User's not available !", Toast.LENGTH_SHORT).show();
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

    private void callSearchResult() {
        LayoutSearchResultBinding binding;
        DialogViewModel dialogViewModel;
        View view;
        RecyclerView.Adapter mSearchAdapter;

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_search_result, null, false);
        view = binding.getRoot();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        alertDialog = builder.create();
        dialogViewModel = new DialogViewModel(context, activity, binding);
        binding.setDialogView(dialogViewModel);

        binding.rvSearchResultList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mSearchAdapter = new AllUsersListSearchAdapter(context, activity, searchUserList, type);
        binding.rvSearchResultList.setAdapter(mSearchAdapter);

        binding.layoutSearch.civBackBtn.setOnClickListener(v -> alertDialog.dismiss());
        binding.layoutSearch.toolbarTitle.setText("Search Result");
        binding.layoutSearch.imgSearch.setVisibility(View.GONE);
        alertDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_state:
                if (!(parent.getSelectedItem().toString().equalsIgnoreCase("Select State"))) {
                    stateID = stateList.get(position - 1).id;
                    getAllCity(stateList.get(position - 1).id);
                } else {
                }
                break;
            case R.id.sp_city:
                if (!(parent.getSelectedItem().toString().equalsIgnoreCase("Select City"))) {
                    cityId = cityList.get(position - 1).id;
                } else {
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}