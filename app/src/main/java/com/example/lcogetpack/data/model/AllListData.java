package com.example.lcogetpack.data.model;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.lcogetpack.R;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.activity.conversation.ConversationActivity;
import com.example.lcogetpack.ui.ui.activity.forumdetails.ForumDetailsActivity;
import com.example.lcogetpack.ui.ui.activity.friendsdetails.FriendsDetailsActivity;
import com.example.lcogetpack.ui.ui.activity.userprofile.ProfileActivity;
import com.example.lcogetpack.ui.ui.fragments.friends.FriendsViewModel;
import com.example.lcogetpack.ui.ui.fragments.notification.NotificationViewModel;
import com.example.lcogetpack.ui.ui.presenter.PlansSelectInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.AppConstants.CONTEXT;
import static com.example.lcogetpack.di.init.ApplicationAppContext.getAppContext;

public class AllListData implements Serializable {

    public String id;
    public String email;
    public String name;
    public String phone;
    public String password;
    public String rowId;
    public String district;
    public String address;
    public String user_type;
    public String price;
    public String country;
    public String status;
    public String is_mso;
    public String is_lco;
    public String is_isd;
    public String is_lba;
    public String parent_id;
    public String image;
    public String file;
    public String s_no;
    public String plans;
    public String title;
    public String detail;
    public String description;
    public String comment;
    public int post_id;
    public int file_type;
    public String likeCount;
    public String commentCount;
    public String user_image;
    public String role;
    public String created_by;
    public String createdAt;
    public String dueration;
    public String speed;
    public String amount;
    public String user_id;
    public String user;
    public String msg_id;
    public String msgs;
    public int type;
    public String body;
    public String forum_id;
    public String answer;
    public String answer_by;
    public String country_name;
    public String state_name;
    public String state_3_code;
    public String state_2_code;
    public String city_name;
    public String state_id;
    public PlansSelectInterface plansSelectInterface;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_mso() {
        return is_mso;
    }

    public void setIs_mso(String is_mso) {
        this.is_mso = is_mso;
    }

    public String getIs_lco() {
        return is_lco;
    }

    public void setIs_lco(String is_lco) {
        this.is_lco = is_lco;
    }

    public String getIs_isd() {
        return is_isd;
    }

    public void setIs_isd(String is_isd) {
        this.is_isd = is_isd;
    }

    public String getIs_lba() {
        return is_lba;
    }

    public void setIs_lba(String is_lba) {
        this.is_lba = is_lba;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public String getPlans() {
        return plans;
    }

    public void setPlans(String plans) {
        this.plans = plans;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDueration() {
        return dueration;
    }

    public void setDueration(String dueration) {
        this.dueration = dueration;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsgs() {
        return msgs;
    }

    public void setMsgs(String msgs) {
        this.msgs = msgs;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getForum_id() {
        return forum_id;
    }

    public void setForum_id(String forum_id) {
        this.forum_id = forum_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer_by() {
        return answer_by;
    }

    public void setAnswer_by(String answer_by) {
        this.answer_by = answer_by;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getState_3_code() {
        return state_3_code;
    }

    public void setState_3_code(String state_3_code) {
        this.state_3_code = state_3_code;
    }

    public String getState_2_code() {
        return state_2_code;
    }

    public void setState_2_code(String state_2_code) {
        this.state_2_code = state_2_code;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public PlansSelectInterface getPlansSelectInterface() {
        return plansSelectInterface;
    }

    public void setPlansSelectInterface(PlansSelectInterface plansSelectInterface) {
        this.plansSelectInterface = plansSelectInterface;
    }

    //------------------------------------------------------------------------------------- Binding Adapter ------------------------------------------------------------

    @BindingAdapter({"bind:image"})
    public static void image(CircleImageView circleImageView, String image) {
        if (image == null) {
            circleImageView.setImageResource(R.mipmap.icon_user);
        } else {
            Glide.with(getAppContext())
                    .load("http://" + image.trim())
                    .into(circleImageView);
        }
    }

    @BindingAdapter({"bind:name"})
    public static void name(TextView view, String name) {
        view.setText(name);
    }

    @BindingAdapter({"bind:phone"})
    public static void phone(TextView view, String phone) {
        view.setText(phone);
    }

    @BindingAdapter({"bind:email"})
    public static void email(TextView view, String email) {
        view.setText(email);
    }

    @BindingAdapter({"bind:id", "bind:is_isd", "bind:is_lba", "bind:is_lco", "bind:is_mso", "bind:user_type"})
    public static void userData(LinearLayout ll_card, String id, String is_isd, String is_lba, String is_lco, String is_mso, String user_type) {

        ll_card.setOnClickListener(view -> {
            if (user_type.equalsIgnoreCase("mso") || user_type.equalsIgnoreCase("lco") || user_type.equalsIgnoreCase("isd")) {
                CONTEXT.startActivity(new Intent(CONTEXT, ProfileActivity.class).
                        putExtra("user_id", id).
                        putExtra("is_mso", is_mso).
                        putExtra("is_lco", is_lco).
                        putExtra("is_isd", is_isd));

            } else if (user_type.equalsIgnoreCase("user") || user_type.equalsIgnoreCase("lbauser")) {
                CONTEXT.startActivity(new Intent(CONTEXT, ProfileActivity.class).
                        putExtra("user_id", id).
                        putExtra("is_mso", "0").
                        putExtra("is_lco", "0").
                        putExtra("is_isd", "0").
                        putExtra("is_lba", "0"));
            } else {
                CONTEXT.startActivity(new Intent(CONTEXT, ProfileActivity.class).
                        putExtra("user_id", id).
                        putExtra("is_mso", "0").
                        putExtra("is_lco", "0").
                        putExtra("is_isd", "0").
                        putExtra("is_lba", is_lba));
            }
        });
    }

    @BindingAdapter({"bind:price"})
    public static void price(TextView view, String price) {
        view.setText(price);
    }

    @BindingAdapter({"bind:id", "bind:name", "bind:user_type"})
    public static void userMessageData(LinearLayout ll_card, String id, String name, String user_type) {

        ll_card.setOnClickListener(view -> {
            CONTEXT.startActivity(new Intent(CONTEXT, ConversationActivity.class).
                    putExtra("user_id", id).
                    putExtra("name", name).
                    putExtra("user_type", user_type));
        });
    }

    @BindingAdapter({"bind:title"})
    public static void title(TextView view, String title) {
        view.setText(title);
    }

    @BindingAdapter({"bind:detail"})
    public static void detail(TextView view, String detail) {
        view.setText(detail);
    }

    @BindingAdapter({"bind:createdAt"})
    public static void createdAt(TextView view, String createdAt) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("E, MMM d");
        Date time_in;
        try {
            if (createdAt != null) {
                time_in = inputFormat.parse(createdAt);
                view.setText(outputFormat.format(time_in));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"bind:msgs"})
    public static void msgs(TextView view, String msgs) {
        view.setText(msgs);
    }

    @BindingAdapter({"bind:body"})
    public static void body(TextView view, String body) {
        view.setText(body);
    }

    @BindingAdapter({"bind:id", "bind:role"})
    public static void forumDetails(LinearLayout ll_card, String id, String role) {

        ll_card.setOnClickListener(view -> CONTEXT.startActivity(new Intent(CONTEXT, ForumDetailsActivity.class).
                putExtra("forum_id", id).putExtra("role", role)));
    }

    @BindingAdapter({"bind:answer_by"})
    public static void answer_by(TextView view, String answer_by) {
        view.setText(answer_by);
    }

    @BindingAdapter({"bind:answer"})
    public static void answer(TextView view, String answer) {
        view.setText(answer);
    }

    @BindingAdapter({"bind:description"})
    public static void description(TextView view, String description) {
        view.setText(description);
    }

    @BindingAdapter({"bind:is_isd", "bind:is_lba", "bind:is_lco", "bind:is_mso"})
    public static void userProffesion(TextView view, String is_isd, String is_lba, String is_lco, String is_mso) {
        String is_mso_ = "";
        String is_lco_ = "";
        String is_isd_ = "";
        String is_lba_ = "";

        if (is_mso.equalsIgnoreCase("1"))
            is_mso_ = "MSO";
        if (is_lco.equalsIgnoreCase("1"))
            is_lco_ = "LCO";
        if (is_isd.equalsIgnoreCase("1"))
            is_isd_ = "ISD";
        if (is_lba.equalsIgnoreCase("1"))
            is_lba_ = "LBA";

        view.setText(is_mso_ + " " + is_lco_ + " " + is_isd_ + " " + is_lba_);
    }

    @BindingAdapter({"bind:id", "bind:user_type"})
    public static void friendsData(LinearLayout ll_card, String id, String user_type) {
        ll_card.setOnClickListener(v -> CONTEXT.startActivity(new Intent(CONTEXT, FriendsDetailsActivity.class).
                putExtra("USER_ID", id).
                putExtra("STATUS", user_type)));
    }

    @BindingAdapter({"bind:status"})
    public static void status(TextView view, String status) {
        if (status.equalsIgnoreCase("Connected")) {
            view.setText(status);
            view.setTextColor(Color.GREEN);
        } else if (status.equalsIgnoreCase("Pending")) {
            view.setText(status);
            view.setTextColor(Color.LTGRAY);
        } else {
            view.setText(status);
            view.setTextColor(Color.RED);
        }
        view.setText(status);
    }

    @BindingAdapter({"bind:id", "bind:status"})
    public static void getSearchData(LinearLayout ll_card, String id, String status) {

        ll_card.setOnClickListener(view -> CONTEXT.startActivity(new Intent(CONTEXT, FriendsDetailsActivity.class).
                putExtra("USER_ID", id).
                putExtra("STATUS", status)));

    }

    @BindingAdapter({"bind:user_image"})
    public static void user_image(CircleImageView circleImageView, String user_image) {
        if (user_image == null) {
            circleImageView.setImageResource(R.mipmap.icon_user);
        } else {
            Glide.with(getAppContext())
                    .load("http://" + user_image.trim())
                    .into(circleImageView);
        }
    }

    @BindingAdapter({"bind:likeCount"})
    public static void likeCount(TextView view, String likeCount) {
        view.setText(likeCount);
    }

    @BindingAdapter({"bind:commentCount"})
    public static void commentCount(TextView view, String commentCount) {
        view.setText(commentCount);
    }

    @BindingAdapter({"bind:file"})
    public static void file(ImageView imageView, String file) {
        if (file == null) {
            imageView.setImageResource(R.mipmap.icon_user);
        } else {
            Glide.with(getAppContext())
                    .load("http://" + file.trim())
                    .into(imageView);
        }
    }

    @BindingAdapter({"bind:comment"})
    public static void comment(TextView view, String comment) {
        view.setText(comment);
    }
}