package com.example.lcogetpack.ui.ui.fragments.message;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.FragmentMessageBinding;

public class MessageFragment extends Fragment {

    public static String TAG = MessageFragment.class.getName();
    public Context context;
    public Activity activity;
    public FragmentMessageBinding binding;
    public MessageViewModel messageViewModel;

    public MessageFragment() {
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

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_message, container, false);
        View view = binding.getRoot();
        messageViewModel = new MessageViewModel(context, activity, binding);
        binding.setMessageView(messageViewModel);

        return view;
    }
}