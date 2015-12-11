package suitup.suitup;

import java.util.List;


import models.*;
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

}
