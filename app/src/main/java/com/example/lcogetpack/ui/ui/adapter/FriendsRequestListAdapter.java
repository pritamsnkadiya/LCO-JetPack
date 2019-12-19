package com.example.lcogetpack.ui.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.databinding.RowFrndrequestNotificationBinding;
import com.example.lcogetpack.ui.ui.fragments.notification.NotificationFragment;
import com.example.lcogetpack.ui.ui.presenter.PlansSelectInterface;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Retrofit;

public class FriendsRequestListAdapter extends RecyclerView.Adapter<FriendsRequestListAdapter.ViewHolder> {

    private static String TAG = FriendsRequestListAdapter.class.getName();
    public Context context;
    public Activity activity;
    private List<AllListData> modelArrayList;
    private LayoutInflater layoutInflater;
    public PostsAdapterListener listener;

    public FriendsRequestListAdapter(Context context, Activity activity, List<AllListData> modelArrayList, PostsAdapterListener listener) {
        this.context = context;
        this.activity = activity;
        this.listener = listener;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowFrndrequestNotificationBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_frndrequest_notification, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.binding.setAllListModel(modelArrayList.get(position));

        holder.binding.textAcceptRequest.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPostClicked(modelArrayList.get(position), 0);

                modelArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, modelArrayList.size());
            }
        });

        holder.binding.textRejectRequest.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPostClicked(modelArrayList.get(position), 1);

                modelArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, modelArrayList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RowFrndrequestNotificationBinding binding;

        public ViewHolder(final RowFrndrequestNotificationBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public interface PostsAdapterListener {
        void onPostClicked(AllListData post, int type);
    }
}