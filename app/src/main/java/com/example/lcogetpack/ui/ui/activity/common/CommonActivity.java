package com.example.lcogetpack.ui.ui.activity.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lcogetpack.R;

public class CommonActivity extends AppCompatActivity {

    public static String TAG = CommonActivity.class.getName();
    public String KEY;
    public NavController navController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        Intent intent = getIntent();
        if (intent != null) {
            KEY = intent.getStringExtra("KEY");
        }

        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);

        switch (KEY) {

            case "1":
                navController.navigate(R.id.notificationFragment);
                break;

            case "2":
                navController.navigate(R.id.friendFragment);
                break;

            case "3":
                navController.navigate(R.id.postFragment);
                break;

            case "4":
                navController.navigate(R.id.trainingFragment);
                break;

            case "5":
                navController.navigate(R.id.subscriptionFragment);
                break;
            default:
                Toast.makeText(this, "Undefined Click !!", Toast.LENGTH_SHORT).show();
        }
    }
}
