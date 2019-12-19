package com.example.lcogetpack.di.api;

import com.example.lcogetpack.data.model.RequestModel;
import com.example.lcogetpack.data.model.ResponsModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

    @Headers({"Content-Type: application/json"})
    @POST("/v1/operators/login")
    Call<ResponsModel> userLogin(@Body RequestModel request);

    @Headers("Content-Type: application/json")
    @GET("/v1/operators/{user_id}")
    Call<ResponsModel> getAllList(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @GET("/v1/operators/{user_id}")
    Call<ResponsModel> getAllLbaList(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @GET("/v1/getStateList")
    Call<ResponsModel> getAllState();

    @Headers("Content-Type: application/json")
    @GET("/v1/getCityByState/{user_id}")
    Call<ResponsModel> getAllCity(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @POST("/v1/searchData")
    Call<ResponsModel> getSearchData(@Body RequestModel requestModel);

    @Headers("Content-Type: application/json")
    @POST("/v1/operators/logout")
    Call<ResponsModel> getLogoutUser();

    @Headers("Content-Type: application/json")
    @GET("/v1/Operators/operator/{user_id}")
    Call<ResponsModel> getUserDetails(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @GET("/v1/Operators/user/{user_id}")
    Call<ResponsModel> getLbaUserDetails(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @GET("/v1/messages")
    Call<ResponsModel> getAllMessagesList();

    @Headers("Content-Type: application/json")
    @GET("/v1/messages/{user_type}/{user_id}")
    Call<ResponsModel> getAllConversation(@Path("user_type") String user_type, @Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @POST("/v1/messages")
    Call<ResponsModel> sendMessageTo(@Body RequestModel requestModel);

    @Headers("Content-Type: application/json")
    @GET("/v1/offers?")
    Call<ResponsModel> getAllOffers();

    @Headers("Content-Type: application/json")
    @POST("/v1/offers")
    Call<ResponsModel> createOffer(@Body RequestModel requestModel);

    @Headers("Content-Type: application/json")
    @GET("/v1/forums")
    Call<ResponsModel> getAllForums();

    @Headers("Content-Type: application/json")
    @GET("v1/forums/{forums_id}")
    Call<ResponsModel> getAllForumsAnswer(@Path("forums_id") String forums_id);

    @Headers("Content-Type: application/json")
    @POST("/v1/forums/answer")
    Call<ResponsModel> submitForumAnswer(@Body RequestModel requestModel);

    @Headers("Content-Type: application/json")
    @POST("/v1/forums")
    Call<ResponsModel> createForum(@Body RequestModel requestModel);

    @Headers("Content-Type: application/json")
    @GET("/v1/operators")
    Call<ResponsModel> getUserDetail();

    @Headers("Content-Type: application/json")
    @GET("/v1/getFriendRequest")
    Call<ResponsModel> getFriendRequest();

    @Headers("Content-Type: application/json")
    @PUT("/v1/friends/{user_id}")
    Call<ResponsModel> acceptFriendRequest(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @DELETE("/v1/friends/{user_id}")
    Call<ResponsModel> declineFriendRequest(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @GET("/v1/friends")
    Call<ResponsModel> getAllFriends();

    @Headers("Content-Type: application/json")
    @GET("/v1/friends/{query}")
    Call<ResponsModel> getAllSearchedFriends(@Path("query") String query);

    @Headers("Content-Type: application/json")
    @GET("/v1/friends/getPersonalInfo/{user_id}")
    Call<ResponsModel> getSearchUserDetails(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @POST("/v1/friends/sendRequest")
    Call<ResponsModel> sendFriendRequest(@Body RequestModel requestModel);

    @Headers("Content-Type: application/json")
    @GET("/v1/posts")
    Call<ResponsModel> getAllPosts();

    @Headers("Content-Type: application/json")
    @POST("/v1/likes")
    Call<ResponsModel> sendLikesRequest(@Body RequestModel requestModel);

    @Headers("Content-Type: application/json")
    @GET("/v1/posts/comments/{post_id}")
    Call<ResponsModel> getAllComments(@Path("post_id") String post_id);

    @Headers("Content-Type: application/json")
    @POST("/v1/comments")
    Call<ResponsModel> sendComments(@Body RequestModel requestModel);

    @Multipart
    @POST("/v1/posts")
    Call<ResponsModel> uploadVideoToServer(@Part("file_type") RequestBody file_type,
                                           @Part("description") RequestBody description,
                                           @Part MultipartBody.Part mediaFile);
}
