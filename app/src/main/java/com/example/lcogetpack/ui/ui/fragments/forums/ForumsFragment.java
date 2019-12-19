package com.example.lcogetpack.ui.ui.fragments.forums;

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
import com.example.lcogetpack.databinding.FragmentForumsBinding;

public class ForumsFragment extends Fragment {
    public static String TAG = ForumsFragment.class.getName();
    public Context context;
    public Activity activity;
    public FragmentForumsBinding binding;
    public ForumViewModel forumViewModel;

    public ForumsFragment() {
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_forums, container, false);
        View view = binding.getRoot();
        forumViewModel = new ForumViewModel(context, activity, binding);
        binding.setForumView(forumViewModel);

        return view;
    }
}
