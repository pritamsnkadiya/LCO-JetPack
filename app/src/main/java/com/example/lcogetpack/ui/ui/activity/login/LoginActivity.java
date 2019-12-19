package com.example.lcogetpack.ui.ui.activity.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.utils.Method;
import com.example.lcogetpack.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    public static String TAG = LoginActivity.class.getName();
    public Context context;
    public Activity activity;
    public ActivityLoginBinding binding;
    public LoginViewModel loginViewModel;
    public String KEY, KEY2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        activity = LoginActivity.this;

        Intent intent = getIntent();
        if (intent != null) {
            KEY = intent.getStringExtra("KEY"); // get operators or user
            KEY2 = intent.getStringExtra("KEY2");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new LoginViewModel(context, activity, binding, KEY, KEY2);
        binding.setLoginView(loginViewModel);

        binding.setPresenter(() -> {
            final String email = binding.getLoginView().email.get();
            final String password = binding.getLoginView().password.get();
            loginViewModel.callLoginApi("sunder@gmail.com", "1234");
       /*     if (checkValidation()) {
                if (isNetworkAvailable()) {
                    loginViewModel.callLoginApi(email, password);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Check your Network", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }*/
        });
    }

    private boolean checkValidation() {
        boolean result = true;
        if (binding.userName.getText().toString().equalsIgnoreCase("")) {
            binding.userName.setError("Enter Valid Email address");
            Toast.makeText(getApplicationContext(), "Enter Valid Email address", Toast.LENGTH_SHORT).show();

            result = false;
        } else if (!((Method.isValidPhone(binding.userName.getText().toString())) || (Method.isValidEmail(binding.userName.getText().toString())))) {
            binding.userName.setError("Enter Valid Email address");
            Toast.makeText(getApplicationContext(), "Enter Valid Email address", Toast.LENGTH_SHORT).show();

            result = false;
        } else if (binding.password.getText().toString().equalsIgnoreCase("")) {
            binding.password.setError("Enter Valid Password");
            Toast.makeText(getApplicationContext(), "Enter Valid Password", Toast.LENGTH_SHORT).show();

            result = false;
        }
        return result;
    }
}
