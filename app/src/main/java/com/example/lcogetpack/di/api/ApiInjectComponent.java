package com.example.lcogetpack.di.api;

import com.example.lcogetpack.ui.ui.activity.createpost.CreatePostActivity;
import com.example.lcogetpack.ui.ui.activity.createpost.CreatePostViewModel;
import com.example.lcogetpack.ui.ui.activity.friendsdetails.FriendsDetailsViewModel;
import com.example.lcogetpack.ui.ui.activity.sendcomment.SendCommentsViewModel;
import com.example.lcogetpack.ui.ui.fragments.forums.ForumViewModel;
import com.example.lcogetpack.ui.ui.fragments.friends.FriendsViewModel;
import com.example.lcogetpack.ui.ui.fragments.home.HomeViewModel;
import com.example.lcogetpack.ui.ui.fragments.message.MessageViewModel;
import com.example.lcogetpack.ui.ui.fragments.notification.NotificationViewModel;
import com.example.lcogetpack.ui.ui.fragments.offer.OffersViewModel;
import com.example.lcogetpack.ui.ui.fragments.post.PostViewModel;
import com.example.lcogetpack.ui.ui.fragments.settings.SettingViewModel;
import com.example.lcogetpack.ui.ui.activity.conversation.ConversationViewModel;
import com.example.lcogetpack.ui.ui.activity.forumdetails.ForumDetailsViewModel;
import com.example.lcogetpack.ui.ui.activity.userprofile.ProfileViewModel;
import com.example.lcogetpack.ui.ui.activity.login.LoginViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiClient.class})
public interface ApiInjectComponent {

    void inject(LoginViewModel loginViewModel);

    void inject(HomeViewModel homeViewModel);

    void inject(SettingViewModel settingViewModel);

    void inject(ProfileViewModel profileViewModel);

    void inject(MessageViewModel messageViewModel);

    void inject(ConversationViewModel conversationViewModel);

    void inject(OffersViewModel offersViewModel);

    void inject(ForumViewModel forumViewModel);

    void inject(ForumDetailsViewModel forumDetailsViewModel);

    void inject(NotificationViewModel notificationViewModel);

    void inject(FriendsViewModel friendsViewModel);

    void inject(FriendsDetailsViewModel friendsDetailsViewModel);

    void inject(PostViewModel postViewModel);

    void inject(SendCommentsViewModel sendCommentsViewModel);

    void inject(CreatePostActivity createPostActivity);

}