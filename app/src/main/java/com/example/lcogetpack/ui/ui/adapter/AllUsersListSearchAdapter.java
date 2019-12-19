package com.example.lcogetpack.ui.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.databinding.LayoutAllUsersListBinding;

import java.util.List;

public class AllUsersListSearchAdapter extends RecyclerView.Adapter<AllUsersListSearchAdapter.ViewHolder> {

    private static String TAG = AllUsersListSearchAdapter.class.getName();
    private Context context;
    private Activity activity;
    private List<AllListData> modelArrayList;
    private String user_type;
    private LayoutInflater layoutInflater;

    public AllUsersListSearchAdapter(Context context, Activity activity, List<AllListData> modelArrayList, String user_type) {
        this.context = context;
        this.activity = activity;
        this.modelArrayList = modelArrayList;
        this.user_type = user_type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutAllUsersListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_all_users_list, parent, false);
        return new AllUsersListSearchAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.binding.setAllListModel(modelArrayList.get(position));
        holder.binding.setUserType(user_type);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LayoutAllUsersListBinding binding;

        public ViewHolder(final LayoutAllUsersListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}