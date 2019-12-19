package com.example.lcogetpack.ui.ui.fragments.post;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.FragmentPostBinding;

public class PostFragment extends Fragment {

    public static String TAG = PostFragment.class.getName();
    public Context context;
    public Activity activity;
    public FragmentPostBinding binding;
    public PostViewModel postViewModel;

    public PostFragment() {
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_post, container, false);
        View view = binding.getRoot();
        postViewModel = new PostViewModel(context, activity, binding);
        binding.setPostView(postViewModel);

        return view;
    }
}
