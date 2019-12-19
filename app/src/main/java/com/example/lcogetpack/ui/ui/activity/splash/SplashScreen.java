package com.example.lcogetpack.ui.ui.activity.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.utils.AppConstants;
import com.example.lcogetpack.data.utils.Method;
import com.example.lcogetpack.databinding.ActivitySplashScreenBinding;
import com.example.lcogetpack.di.init.SessionManager;
import com.example.lcogetpack.ui.ui.activity.dashboard.DashbordActivity;
import com.example.lcogetpack.ui.ui.activity.main.MainActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.lcogetpack.di.init.ApplicationAppContext.getAppContext;

public class SplashScreen extends AppCompatActivity {

    public Handler handler;
    public SessionManager session;
    public ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        session = new SessionManager(getApplicationContext());
        handler = new Handler();

        AppConstants.CONTEXT = SplashScreen.this;
        AppConstants.TOKEN = Method.getPreferences(getAppContext(), "Authorization");
        getKeyHash();

        handler.postDelayed(() -> {
            if (session.isLoggedIn()) {
                startActivity(new Intent(SplashScreen.this, DashbordActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
