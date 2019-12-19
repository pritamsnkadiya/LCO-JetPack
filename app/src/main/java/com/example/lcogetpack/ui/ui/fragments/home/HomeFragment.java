package com.example.lcogetpack.ui.ui.fragments.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    public static String TAG = HomeFragment.class.getName();
    public Context context;
    public Activity activity;
    public FragmentHomeBinding binding;
    public HomeViewModel homeViewModel;

    public HomeFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(container.getContext());
        }

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        homeViewModel = new HomeViewModel(context, activity, binding);
        binding.setHomeView(homeViewModel);
        return view;

    }
}