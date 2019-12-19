package com.example.lcogetpack.ui.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.TypeModel;
import com.example.lcogetpack.databinding.RowPostImageBinding;
import com.example.lcogetpack.databinding.RowPostTextDiscriptionBinding;
import com.example.lcogetpack.databinding.RowPostVideoBinding;
import com.example.lcogetpack.ui.ui.activity.sendcomment.SendCommentsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Posts_Adapter extends RecyclerView.Adapter {

    private Context mContext;
    private Activity activity;
    private List<AllListData> modelArrayList;
    private LayoutInflater layoutInflater;
    public RowPostTextDiscriptionBinding textDiscriptionBinding;
    public RowPostImageBinding imageBinding;
    public RowPostVideoBinding videoBinding;
    public PostsAdapterListener listener;
    private String is_mso = "";
    private String is_lco = "";
    private String is_isd = "";
    private String is_lba = "";

    public Posts_Adapter(Context context, Activity activity, List<AllListData> modelArrayList, PostsAdapterListener listener) {
        this.modelArrayList = modelArrayList;
        this.mContext = context;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case TypeModel.TEXT_TYPE:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                textDiscriptionBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_post_text_discription, parent, false);
                return new TextTypeViewHolder(textDiscriptionBinding);

            case TypeModel.IMAGE_TYPE:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                imageBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_post_image, parent, false);
                return new ImageTypeViewHolder(imageBinding);

            case TypeModel.VIDEO_TYPE:
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(parent.getContext());
                }
                videoBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_post_video, parent, false);
                return new VideoTypeViewHolder(videoBinding);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (modelArrayList.get(position).file_type) {
            case 0:
                return TypeModel.IMAGE_TYPE;
            case 1:
                return TypeModel.VIDEO_TYPE;
            default:
                return TypeModel.TEXT_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        AllListData object = modelArrayList.get(listPosition);
        if (object != null) {
            switch (object.file_type) {
                case TypeModel.IMAGE_TYPE:

                    imageBinding.setAllListModel(object);

                    ((ImageTypeViewHolder) holder).binding.likeBtnImage.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            listener.onPostClicked(modelArrayList.get(listPosition), 1);
                        } else {
                            listener.onPostClicked(modelArrayList.get(listPosition), 0);
                        }
                    });

                    if (object.is_mso.equalsIgnoreCase("1"))
                        is_mso = "MSO";
                    if (object.is_lco.equalsIgnoreCase("1"))
                        is_mso = "LCO";
                    if (object.is_isd.equalsIgnoreCase("1"))
                        is_mso = "ISD";
                    if (object.is_lba.equalsIgnoreCase("1"))
                        is_mso = "LBA";

                    ((ImageTypeViewHolder) holder).binding.commentBtnImage.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, SendCommentsActivity.class).
                            putExtra("id", String.valueOf(object.id)).
                            putExtra("description", object.description).
                            putExtra("file", object.file).
                            putExtra("file_type", String.valueOf(object.file_type)).
                            putExtra("comment", object.comment).
                            putExtra("post_id", String.valueOf(object.post_id)).
                            putExtra("name", object.name).
                            putExtra("user_image", object.user_image).
                            putExtra("is_lco", is_lco).
                            putExtra("is_mso", is_mso).
                            putExtra("is_isd", is_isd).
                            putExtra("is_lba", is_lba)));

                    break;
                case TypeModel.VIDEO_TYPE:
                    videoBinding.setAllListModel(object);

                    ((VideoTypeViewHolder) holder).binding.playVideo.setOnClickListener(v -> {
                        ((VideoTypeViewHolder) holder).binding.imageViewPostVideo.setVideoPath("http://" + object.file);
                        ((VideoTypeViewHolder) holder).binding.imageViewPostVideo.setMediaController(new MediaController(mContext));
                        ((VideoTypeViewHolder) holder).binding.imageViewPostVideo.requestFocus();
                        ((VideoTypeViewHolder) holder).binding.imageViewPostVideo.start();
                    });

                    ((VideoTypeViewHolder) holder).binding.likeBtnVideo.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            listener.onPostClicked(modelArrayList.get(listPosition), 1);
                        } else {
                            listener.onPostClicked(modelArrayList.get(listPosition), 0);
                        }
                    });

                    if (object.is_mso.equalsIgnoreCase("1"))
                        is_mso = "MSO";
                    if (object.is_lco.equalsIgnoreCase("1"))
                        is_mso = "LCO";
                    if (object.is_isd.equalsIgnoreCase("1"))
                        is_mso = "ISD";
                    if (object.is_lba.equalsIgnoreCase("1"))
                        is_mso = "LBA";

                    ((VideoTypeViewHolder) holder).binding.commentBtnVideo.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, SendCommentsActivity.class).
                            putExtra("id", String.valueOf(object.id)).
                            putExtra("description", object.description).
                            putExtra("file", object.file).
                            putExtra("file_type", String.valueOf(object.file_type)).
                            putExtra("comment", object.comment).
                            putExtra("post_id", String.valueOf(object.post_id)).
                            putExtra("name", object.name).
                            putExtra("user_image", object.user_image).
                            putExtra("is_lco", is_lco).
                            putExtra("is_mso", is_mso).
                            putExtra("is_isd", is_isd).
                            putExtra("is_lba", is_lba)));

                    break;

                default:
                case TypeModel.TEXT_TYPE:
                    textDiscriptionBinding.setAllListModel(object);

                    ((TextTypeViewHolder) holder).binding.likeBtnText.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            listener.onPostClicked(modelArrayList.get(listPosition), 1);
                        } else {
                            listener.onPostClicked(modelArrayList.get(listPosition), 0);
                        }
                    });

                    if (object.is_mso.equalsIgnoreCase("1"))
                        is_mso = "MSO";
                    if (object.is_lco.equalsIgnoreCase("1"))
                        is_mso = "LCO";
                    if (object.is_isd.equalsIgnoreCase("1"))
                        is_mso = "ISD";
                    if (object.is_lba.equalsIgnoreCase("1"))
                        is_mso = "LBA";

                    ((TextTypeViewHolder) holder).binding.commentBtnText.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, SendCommentsActivity.class).
                            putExtra("id", String.valueOf(object.id)).
                            putExtra("description", object.description).
                            putExtra("file", object.file).
                            putExtra("file_type", String.valueOf(object.file_type)).
                            putExtra("comment", object.comment).
                            putExtra("post_id", String.valueOf(object.post_id)).
                            putExtra("name", object.name).
                            putExtra("user_image", object.user_image).
                            putExtra("is_lco", is_lco).
                            putExtra("is_mso", is_mso).
                            putExtra("is_isd", is_isd).
                            putExtra("is_lba", is_lba)));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class TextTypeViewHolder extends RecyclerView.ViewHolder {

        private final RowPostTextDiscriptionBinding binding;

        public TextTypeViewHolder(final RowPostTextDiscriptionBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        private final RowPostImageBinding binding;

        public ImageTypeViewHolder(final RowPostImageBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public class VideoTypeViewHolder extends RecyclerView.ViewHolder {

        private final RowPostVideoBinding binding;

        public VideoTypeViewHolder(final RowPostVideoBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public interface PostsAdapterListener {

        void onPostClicked(AllListData post, int type);
    }
}