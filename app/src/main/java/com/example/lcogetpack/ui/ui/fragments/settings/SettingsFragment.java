package com.example.lcogetpack.ui.ui.fragments.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    public static String TAG = SettingsFragment.class.getName();
    public Context context;
    public Activity activity;
    public FragmentSettingsBinding binding;
    public SettingViewModel settingViewModel;

    public SettingsFragment() {
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_settings, container, false);
        View view = binding.getRoot();
        settingViewModel = new SettingViewModel(context, activity, binding);
        binding.setSettingView(settingViewModel);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
