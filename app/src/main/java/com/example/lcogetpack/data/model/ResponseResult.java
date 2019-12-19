package com.example.lcogetpack.data.model;

import java.io.Serializable;
import java.util.List;

public class ResponseResult implements Serializable {

    public String id;
    public String token;
    public String name;
    public String email;
    public String message;
    public String phone;
    public String is_lco;
    public String is_mso;
    public String is_isd;
    public List<AllListData> channels;
    public List<AllListData> packages;
    public List<AllListData> planList;
    public List<AllListData> plans;
    public List<AllListData> userlist;
    public List<AllListData> msgs;
    public List<AllListData> forumList;
    public List<AllListData> answerList;
    public ForumResponse Forum;
    public String image;
    public String file;
    public String address;
    public List<AllListData> channelList;
    public List<AllListData> packageList;
    public List<AllListData> offerList;
    public List<AllListData> friendRequest;
    public List<AllListData> Broadcast;
    public List<AllListData> posts;
    public List<AllListData> reactionDetails;
    public LoggedUser loginUser;
    public List<AllListData> userList;
    public List<AllListData> stateList;
    public List<AllListData> cityList;
    public String user_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIs_lco() {
        return is_lco;
    }

    public void setIs_lco(String is_lco) {
        this.is_lco = is_lco;
    }

    public String getIs_mso() {
        return is_mso;
    }

    public void setIs_mso(String is_mso) {
        this.is_mso = is_mso;
    }

    public String getIs_isd() {
        return is_isd;
    }

    public void setIs_isd(String is_isd) {
        this.is_isd = is_isd;
    }

    public List<AllListData> getChannels() {
        return channels;
    }

    public void setChannels(List<AllListData> channels) {
        this.channels = channels;
    }

    public List<AllListData> getPackages() {
        return packages;
    }

    public void setPackages(List<AllListData> packages) {
        this.packages = packages;
    }

    public List<AllListData> getPlanList() {
        return planList;
    }

    public void setPlanList(List<AllListData> planList) {
        this.planList = planList;
    }

    public List<AllListData> getPlans() {
        return plans;
    }

    public void setPlans(List<AllListData> plans) {
        this.plans = plans;
    }

    public List<AllListData> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<AllListData> userlist) {
        this.userlist = userlist;
    }

    public List<AllListData> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<AllListData> msgs) {
        this.msgs = msgs;
    }

    public List<AllListData> getForumList() {
        return forumList;
    }

    public void setForumList(List<AllListData> forumList) {
        this.forumList = forumList;
    }

    public List<AllListData> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AllListData> answerList) {
        this.answerList = answerList;
    }

    public ForumResponse getForum() {
        return Forum;
    }

    public void setForum(ForumResponse forum) {
        Forum = forum;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AllListData> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<AllListData> channelList) {
        this.channelList = channelList;
    }

    public List<AllListData> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<AllListData> packageList) {
        this.packageList = packageList;
    }

    public List<AllListData> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<AllListData> offerList) {
        this.offerList = offerList;
    }

    public List<AllListData> getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(List<AllListData> friendRequest) {
        this.friendRequest = friendRequest;
    }

    public List<AllListData> getBroadcast() {
        return Broadcast;
    }

    public void setBroadcast(List<AllListData> broadcast) {
        Broadcast = broadcast;
    }

    public List<AllListData> getPosts() {
        return posts;
    }

    public void setPosts(List<AllListData> posts) {
        this.posts = posts;
    }

    public List<AllListData> getReactionDetails() {
        return reactionDetails;
    }

    public void setReactionDetails(List<AllListData> reactionDetails) {
        this.reactionDetails = reactionDetails;
    }

    public LoggedUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoggedUser loginUser) {
        this.loginUser = loginUser;
    }

    public List<AllListData> getUserList() {
        return userList;
    }

    public void setUserList(List<AllListData> userList) {
        this.userList = userList;
    }

    public List<AllListData> getStateList() {
        return stateList;
    }

    public void setStateList(List<AllListData> stateList) {
        this.stateList = stateList;
    }

    public List<AllListData> getCityList() {
        return cityList;
    }

    public void setCityList(List<AllListData> cityList) {
        this.cityList = cityList;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
