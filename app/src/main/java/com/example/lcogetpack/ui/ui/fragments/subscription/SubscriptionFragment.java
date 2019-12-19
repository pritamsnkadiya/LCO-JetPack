package com.example.lcogetpack.ui.ui.fragments.subscription;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lcogetpack.R;

public class SubscriptionFragment extends Fragment {

    public SubscriptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscription, container, false);
    }
}
