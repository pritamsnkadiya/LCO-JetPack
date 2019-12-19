package com.example.lcogetpack.ui.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.TypeModel;
import com.example.lcogetpack.databinding.MyMessageBinding;
import com.example.lcogetpack.databinding.TheirMessageBinding;

import java.util.List;

public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    public Context context;
    public Activity activity;
    public int total_types;
    private List<AllListData> modelArrayList;
    private LayoutInflater layoutInflater;
    private MyMessageBinding myMessageBinding;
    private TheirMessageBinding theirMessageBinding;

    public MultiViewTypeAdapter(Context context, Activity activity, List<AllListData> modelArrayList) {
        this.context = context;
        this.activity = activity;
        this.modelArrayList = modelArrayList;
        total_types = this.modelArrayList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TypeModel.MY_MESSAGE:

                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                myMessageBinding = DataBindingUtil.inflate(layoutInflater, R.layout.my_message,
                        parent, false);
                return new MyMessageViewHolder(myMessageBinding);

            case TypeModel.THEIR_MESSAGE:

                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                theirMessageBinding = DataBindingUtil.inflate(layoutInflater, R.layout.their_message,
                        parent, false);
                return new TheirMessageViewHolder(theirMessageBinding);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (modelArrayList.get(position).type) {
            case 0:
                return TypeModel.MY_MESSAGE;
            case 1:
                return TypeModel.THEIR_MESSAGE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        AllListData object = modelArrayList.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case TypeModel.MY_MESSAGE:
                    myMessageBinding.setAllListModel(object);

                    break;

                case TypeModel.THEIR_MESSAGE:
                    theirMessageBinding.setAllListModel(object);

                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class MyMessageViewHolder extends RecyclerView.ViewHolder {

        private final MyMessageBinding binding;

        public MyMessageViewHolder(final MyMessageBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public class TheirMessageViewHolder extends RecyclerView.ViewHolder {

        private final TheirMessageBinding binding;

        public TheirMessageViewHolder(final TheirMessageBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

}