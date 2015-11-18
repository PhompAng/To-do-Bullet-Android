package th.in.phompang.todobullet;

import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by Pichai Sivawat on 18/11/2558.
 */
public interface APIService {
    @Multipart
    @POST("task/update")
    Call<String> updateImage( @Query(value = "title") String title, @Part("description\"; filename=\"1.jpg\" ") RequestBody image, @Query(value = "time") String datetime, @Query(value = "type") String type, @Query(value = "local_id") String local_id, @Query(value = "token") String token);

    @Multipart
    @POST("task/add")
    Call<String> uploadImage( @Query(value = "title") String title, @Part("description\"; filename=\"1.jpg\" ") RequestBody image, @Query(value = "time") String datetime, @Query(value = "type") String type, @Query(value = "local_id") String local_id, @Query(value = "token") String token);
}
