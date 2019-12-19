package com.example.lcogetpack.ui.ui.fragments.friends;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lcogetpack.R;
import com.example.lcogetpack.databinding.FragmentFriendBinding;

public class FriendFragment extends Fragment {

    public static String TAG = FriendFragment.class.getName();
    public Context context;
    public Activity activity;
    public FragmentFriendBinding binding;
    public FriendsViewModel friendsViewModel;

    public FriendFragment() {
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_friend, container, false);
        View view = binding.getRoot();
        friendsViewModel = new FriendsViewModel(context, activity, binding);
        binding.setFriendView(friendsViewModel);

        return view;
    }
}
