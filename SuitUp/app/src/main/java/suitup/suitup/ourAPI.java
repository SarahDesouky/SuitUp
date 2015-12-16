package suitup.suitup;

import java.util.List;

import models.*;
import models.Post;
import models.User;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.DELETE;
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

    @GET("/v/users/{id}/posts")
    void getMyPostsByID(@Path("id") String id, Callback<List<Post>> callback);

    @GET("/users/")
    void getAllUsers(Callback<List<User>> callback);

    @GET("/users/{twitter_id}/friends")
    void getFriends(@Path("twitter_id") String id, Callback<List<User>> callback);

    @GET("/friends/{friend_id}")
    void getFriend(@Path("friend_id") String id, Callback<User> callback);

    @FormUrlEncoded
    @POST("/users/{twitter_id}/friends/")
    void addFriend(@Path("twitter_id") String userId, @Field("user[id]") String friendId, Callback<User> callback);

    @DELETE("/users/{twitter_id}/friends/{id}")
    void removeFriend(@Path("twitter_id") String id, @Path("id") String friendId, Callback<User> callback);

    
    @GET("/users/{twitter_id}/friends/{id}")
    void isFriend(@Path("twitter_id") String id, @Path("id") String friendId, Callback<User> callback);

    @FormUrlEncoded
    @POST("/posts/{post_id}")
    void AddComment(@Path("post_id") String postid,@Field("comment[owner_id]")String owner_id,@Field("comment[text]") String text,@Field("comment[post_id]") String post_id , Callback<Comment> callback);

    @GET("/posts/{post_id}/comments")
    void getAllComments(@Path("post_id") String id, Callback<List<models.Comment>> callback);

    @FormUrlEncoded
    @POST("/users/post")
    void  AddPost(
                 @Field("post[owner_id]")String owner_id,
                 @Field("post[profile_id]") String profile_id,
                 @Field("post[text]") String text ,
                 @Field("post[image_url]") String image ,
                 Callback<Post> callback);

    @GET("/users/{twitter_id}/messages/threads")
    void getAllThreads(@Path("twitter_id") String id, Callback<List<MessageThread>>callback);

    @GET("/users/posts")
    void getPosts(Callback<List<Post>> callback);
}
