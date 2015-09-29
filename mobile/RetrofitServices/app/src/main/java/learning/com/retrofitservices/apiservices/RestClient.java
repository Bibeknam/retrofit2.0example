package learning.com.retrofitservices.apiservices;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import learning.com.retrofitservices.apiservices.model.User;
import learning.com.retrofitservices.apiservices.service.APIService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RestClient{

    private static RestClient instance = null;

    private ResultReadyCallback callback;

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private APIService service;
    List<User> users = null;
    boolean success = false;


    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(APIService.class);
    }

    public List<User> getUsers() {
        Call<List<User>> userlist = service.users();
        userlist.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response) {
                if (response.isSuccess()) {
                    users = response.body();
                    callback.resultReady(users);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                  Log.e("REST", t.getMessage());
            }
        });
        return users;
    }

    public void setCallback(ResultReadyCallback callback) {
        this.callback = callback;
    }

    public boolean createUser(final Context ctx, User user) {
        Call<User> u = service.createUser(user);
        u.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                success = response.isSuccess();
                if(success) {
                    Toast.makeText(ctx, "User Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctx, "Couldn't create user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.w("REST", t.getMessage());
                Toast.makeText(ctx, "Couldn't create user", Toast.LENGTH_SHORT).show();

            }
        });
        return success;
    }

    public static RestClient getInstance() {
        if(instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    public interface ResultReadyCallback {
        public void resultReady(List<User> users);
    }

}