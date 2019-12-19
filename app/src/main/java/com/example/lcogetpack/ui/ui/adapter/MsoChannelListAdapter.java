package com.example.lcogetpack.ui.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.databinding.LayoutChannelsPackagesListItemsBinding;

import java.util.ArrayList;
import java.util.List;

public class MsoChannelListAdapter extends RecyclerView.Adapter<MsoChannelListAdapter.ViewHolder> {

    private static String TAG = MsoChannelListAdapter.class.getName();
    private Context context;
    private Activity activity;
    private List<AllListData> modelArrayList;
    private LayoutInflater layoutInflater;

    public MsoChannelListAdapter(Context context, Activity activity, ArrayList<AllListData> modelArrayList) {
        this.context = context;
        this.activity = activity;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutChannelsPackagesListItemsBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_channels_packages_list_items,
                parent, false);
        return new MsoChannelListAdapter.ViewHolder(binding);
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

        private final LayoutChannelsPackagesListItemsBinding binding;

        public ViewHolder(final LayoutChannelsPackagesListItemsBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}