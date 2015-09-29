package learning.com.retrofitservices.apiservices.service;
import java.util.List;

import learning.com.retrofitservices.apiservices.model.User;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface APIService {
    @POST("/api/user")
    Call<User> createUser(@Body User user);

    @GET("/api/user")
    Call<List<User>> users();
}
