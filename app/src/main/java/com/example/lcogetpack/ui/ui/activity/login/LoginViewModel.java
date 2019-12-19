package com.example.lcogetpack.ui.ui.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import com.example.lcogetpack.ui.ui.activity.dashboard.DashbordActivity;
import com.example.lcogetpack.data.model.RequestModel;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.Method;
import com.example.lcogetpack.databinding.ActivityLoginBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.di.init.SessionManager;
import com.example.lcogetpack.ui.ui.activity.registration.RegistarionActivity;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Observable;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.hud;
import static com.example.lcogetpack.data.utils.Method.logInProgressDialogCall;

public class LoginViewModel extends Observable {

    public static String TAG = LoginViewModel.class.getName();
    private Context context;
    private Activity activity;
    private ActivityLoginBinding binding;
    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");
    private RequestModel requestModel;
    public SessionManager session;
    public String KEY, KEY2;
    @Inject
    Retrofit retrofit;

    public LoginViewModel(Context context, Activity activity, ActivityLoginBinding binding, String KEY, String KEY2) {

        this.binding = binding;
        this.activity = activity;
        this.context = context;
        this.KEY = KEY;
        this.KEY2 = KEY2;

        session = new SessionManager(context);
        ((ApplicationAppContext) activity.getApplication()).getNetworkComponent().inject(LoginViewModel.this);
        binding.tvRegisterHere.setOnClickListener(v -> context.startActivity(new Intent(context, RegistarionActivity.class).putExtra("KEY", KEY2)));
    }

    public void callLoginApi(String email, String password) {
        logInProgressDialogCall(activity);

        requestModel = new RequestModel();
        requestModel.email = email;
        requestModel.password = password;

        Method.savePreferences(context, "is_mso", "0");
        Method.savePreferences(context, "is_lco", "0");
        Method.savePreferences(context, "is_isd", "0");
        Method.savePreferences(context, "is_lba", "0");

        ApiInterface mService = retrofit.create(ApiInterface.class);
        Call<ResponsModel> responseModelCall = mService.userLogin(requestModel);
        responseModelCall.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                if (response.isSuccessful()) {
                    try {
                        Method.savePreferences(context, "Authorization", response.body().result.token);
                        Method.savePreferences(context, "user_id", response.body().result.loginUser.id);
                        Method.savePreferences(context, "parent_id", response.body().result.loginUser.parent_id);

                        session.createLoginSession(response.body().result.token, response.body().status);

                        if (response.body().result.loginUser.is_mso == 1) {
                            Method.savePreferences(context, "is_mso", "1");
                        }
                        if (response.body().result.loginUser.is_lco == 1) {
                            Method.savePreferences(context, "is_lco", "1");
                        }
                        if (response.body().result.loginUser.is_isd == 1) {
                            Method.savePreferences(context, "is_isd", "1");
                        }
                        if (response.body().result.loginUser.is_lba == 1) {
                            Method.savePreferences(context, "is_lba", "1");
                        }

                        hud.dismiss();
                        Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                        context.startActivity(new Intent(context, DashbordActivity.class).putExtra("KEY", KEY));
                        activity.finish();


                    } catch (Exception e) {
                        hud.dismiss();
                        Toast.makeText(context, "Please Enter Valid credentials ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    hud.dismiss();
                    hud = KProgressHUD.create(activity)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setDimAmount(0.5f)
                            .setLabel("Alert")
                            .setDetailsLabel("Please Enter Valid credentials !")
                            .setCancellable(dialogInterface -> {
                                Toast.makeText(context, "Please " +
                                        "Try again !", Toast
                                        .LENGTH_SHORT).show();
                                hud.dismiss();
                            });
                    hud.show();
                }
            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                hud.dismiss();
                Toast.makeText(context, "Enter Valid credentials", Toast.LENGTH_SHORT).show();
                Log.d("Login Request", "krishna Failed request : " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
