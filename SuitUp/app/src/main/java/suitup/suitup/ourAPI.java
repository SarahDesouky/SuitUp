package suitup.suitup;

import java.util.List;


import models.*;
import models.Post;
import models.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface ourAPI {

    @GET("/users/{twitter_id}")
    void getUser(@Path("twitter_id") String id, Callback<models.User> callback);


    @FormUrlEncoded
    @POST("/users/")
    void CreateUser(@Field("user[fname]") String fname, @Field("user[lname]") String lname,
                    @Field("user[date_of_birth]") String date_of_birth,
                    @Field("user[email]") String email,
                    @Field("user[avatar_url]") String avatar_url,
                    @Field("user[gender]") String gender,
                    @Field("user[country]") String country,
                    @Field("user[twitter_id]") String twitter_id,
                    Callback<User> callback);

    @FormUrlEncoded
    @PUT("/users/{twitter_id}")
    void updateUser(@Path("twitter_id") String id, @Field("user[fname]") String fname, @Field("user[lname]") String lname,
                    @Field("user[email]") String email,
                    @Field("user[country]") String country,
                    Callback<User> callback );

    @GET("/users/{twitter_id}/posts")
    void getMyPosts(@Path("twitter_id") String id, Callback<List<Post>> callback);
}
