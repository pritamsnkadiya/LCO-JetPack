package com.example.lcogetpack.ui.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.databinding.LayoutAllUserSearchListBinding;

import java.util.List;

public class FriendsSearchListAdapter extends RecyclerView.Adapter<FriendsSearchListAdapter.ViewHolder> {

    private static String TAG = FriendsSearchListAdapter.class.getName();
    public Context context;
    public Activity activity;
    private List<AllListData> modelArrayList;
    private LayoutInflater layoutInflater;

    public FriendsSearchListAdapter(Context context, Activity activity, List<AllListData> modelArrayList) {
        this.context = context;
        this.activity = activity;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutAllUserSearchListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_all_user_search_list, parent, false);
        return new FriendsSearchListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.binding.setAllListModel(modelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LayoutAllUserSearchListBinding binding;

        public ViewHolder(final LayoutAllUserSearchListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}