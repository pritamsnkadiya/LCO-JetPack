package com.example.lcogetpack.ui.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.databinding.LayoutForumListBinding;

import java.util.List;


public class ForumListAdapter extends RecyclerView.Adapter<ForumListAdapter.ViewHolder> {

    private static String TAG = ForumListAdapter.class.getName();
    private Context context;
    private Activity activity;
    private List<AllListData> modelArrayList;
    private LayoutInflater layoutInflater;

    public ForumListAdapter(Context context, Activity activity, List<AllListData> modelArrayList) {
        this.context = context;
        this.activity = activity;
        this.modelArrayList = modelArrayList;
    }

    public void filterList(List<AllListData> filterdNames) {
        this.modelArrayList = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutForumListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_forum_list, parent, false);
        return new ViewHolder(binding);
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

        private final LayoutForumListBinding binding;

        public ViewHolder(final LayoutForumListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}