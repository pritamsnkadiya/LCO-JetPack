package com.example.lcogetpack.ui.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.databinding.LayoutAllFriendsListBinding;
import com.example.lcogetpack.databinding.LayoutAllUsersListBinding;
import com.example.lcogetpack.ui.ui.activity.friendsdetails.FriendsDetailsActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private static String TAG = FriendListAdapter.class.getName();
    private Context context;
    private Activity activity;
    private List<AllListData> modelArrayList;
    private LayoutInflater layoutInflater;

    public FriendListAdapter(Context context, Activity activity, List<AllListData> modelArrayList) {
        this.context = context;
        this.activity = activity;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutAllFriendsListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_all_friends_list, parent, false);
        return new FriendListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.binding.setAllListModel(modelArrayList.get(position));
        holder.binding.setUserType("Connected");
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LayoutAllFriendsListBinding binding;

        public ViewHolder(final LayoutAllFriendsListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}