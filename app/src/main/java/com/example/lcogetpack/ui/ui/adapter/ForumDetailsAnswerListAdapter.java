package com.example.lcogetpack.ui.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.databinding.LayoutForumAnswerListBinding;

import java.util.List;

public class ForumDetailsAnswerListAdapter extends RecyclerView.Adapter<ForumDetailsAnswerListAdapter.ViewHolder> {

    private static String TAG = ForumDetailsAnswerListAdapter.class.getName();
    private Context context;
    private Activity activity;
    private List<AllListData> modelArrayList;
    private LayoutInflater layoutInflater;

    public ForumDetailsAnswerListAdapter(Context context, Activity activity, List<AllListData> modelArrayList) {
        this.context = context;
        this.activity = activity;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutForumAnswerListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_forum_answer_list, parent, false);
        return new ForumDetailsAnswerListAdapter.ViewHolder(binding);
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

        private final LayoutForumAnswerListBinding binding;

        public ViewHolder(final LayoutForumAnswerListBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}