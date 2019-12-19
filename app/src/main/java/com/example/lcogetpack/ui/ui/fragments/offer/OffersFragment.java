package com.example.lcogetpack.ui.ui.fragments.offer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.FragmentOfferBinding;

public class OffersFragment extends Fragment {
    public static String TAG = OffersFragment.class.getName();
    public Context context;
    public Activity activity;
    public FragmentOfferBinding binding;
    public OffersViewModel offersViewModel;

    public OffersFragment() {
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_offer, container, false);
        View view = binding.getRoot();
        offersViewModel = new OffersViewModel(context, activity, binding);
        binding.setOfferView(offersViewModel);

        return view;
    }
}