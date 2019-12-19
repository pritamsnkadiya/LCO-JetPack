package com.example.lcogetpack.ui.ui.fragments.notification;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.lcogetpack.R;
import com.example.lcogetpack.data.model.AllListData;
import com.example.lcogetpack.data.model.ResponsModel;
import com.example.lcogetpack.data.utils.ItemOffsetDecoration;
import com.example.lcogetpack.databinding.FragmentNotificationBinding;
import com.example.lcogetpack.di.api.ApiInterface;
import com.example.lcogetpack.di.init.ApplicationAppContext;
import com.example.lcogetpack.ui.ui.adapter.FriendsRequestListAdapter;
import com.example.lcogetpack.ui.ui.adapter.Notification_Other_Adapter;
import com.example.lcogetpack.ui.ui.fragments.forums.ForumsFragment;
import com.example.lcogetpack.ui.ui.presenter.PlansSelectInterface;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.lcogetpack.data.utils.Method.hud;
import static com.example.lcogetpack.data.utils.Method.logInProgressDialogCall;

public class NotificationFragment extends Fragment {

    public static String TAG = NotificationFragment.class.getName();
    public Context context;
    public Activity activity;
    public FragmentNotificationBinding binding;
    public NotificationViewModel notificationViewModel;

    public NotificationFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (inflater == null) {
            inflater = LayoutInflater.from(container.getContext());
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        View view = binding.getRoot();

        notificationViewModel = new NotificationViewModel(context, activity, binding);
        binding.setNotificationView(notificationViewModel);

        return view;
    }
}
