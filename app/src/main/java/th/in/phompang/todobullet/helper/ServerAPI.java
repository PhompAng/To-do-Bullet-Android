package th.in.phompang.todobullet.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import th.in.phompang.todobullet.APIService;
import th.in.phompang.todobullet.app.AppConfig;
import th.in.phompang.todobullet.app.AppController;

/**
 * Created by Pichai Sivawat on 18/11/2558.
 */
public class ServerAPI {

    SharedPreferences pref;
    private Context ctx;
    private SQLiteHandler db;

    public ServerAPI(Context context) {
        this.ctx = context;
        db = new SQLiteHandler(this.ctx);
    }

    public void addTask(final String title, final String description, final String datetime, final int type, final long local_id) {
        String tag_string_req = "req_add_task";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_ADD_TASK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    boolean error = obj.getBoolean("error");

                    if(!error) {

                    } else {
                        String errorMsg = obj.getString("error_msg");
                        Toast.makeText(ctx, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, "JSON Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("description", description);
                params.put("type", String.valueOf(type));
                params.put("token", getToken());
                params.put("time", datetime);
                params.put("local_id", String.valueOf(local_id));
                Log.d("params", params.toString());

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void addTask(final String title, final Uri image, final String datetime, final int type, final long local_id) {
        File file = new File(getRealPathFromURI(ctx, image));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://203.170.193.91:7000/")
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<String> call = service.uploadImage(title, requestBody, datetime, Integer.toString(type), Long.toString(local_id), getToken());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(retrofit.Response<String> response, Retrofit retrofit) {
                Log.v("response", Integer.toString(response.code()));
                Log.v("message", response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Upload", t.getMessage());
            }
        });
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void updateTask(final String title, final Uri image, final String datetime, final int type, final long local_id) {
        Log.d("image", image.toString());
        File file = new File(getRealPathFromURI(ctx, image));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://203.170.193.91:7000/")
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<String> call = service.updateImage(title, requestBody, datetime, Integer.toString(type), Long.toString(local_id), getToken());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(retrofit.Response<String> response, Retrofit retrofit) {
                Log.v("response", Integer.toString(response.code()));
                Log.v("message", response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Upload", t.getMessage());
            }
        });
    }

    public void updateTask(final String title, final String description, final String datetime, final int type, final long local_id) {
        String tag_string_req = "req_update_task";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_UPDATE_TASK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    boolean error = obj.getBoolean("error");

                    if(!error) {

                    } else {
                        String errorMsg = obj.getString("error_msg");
                        Toast.makeText(ctx, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, "JSON Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("description", description);
                params.put("type", String.valueOf(type));
                params.put("token", getToken());
                params.put("time", datetime);
                params.put("local_id", String.valueOf(local_id));
                Log.d("params", params.toString());

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void removeTask(final long local_id) {
        String tag_string_req = "req_remove_task";

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REMOVE_TASK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    boolean error = obj.getBoolean("error");

                    if(!error) {

                    } else {
                        String errorMsg = obj.getString("error_msg");
                        Toast.makeText(ctx, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx, "JSON Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("token", getToken());
                params.put("local_id", String.valueOf(local_id));
                Log.d("params", params.toString());

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public String getToken() {
        pref = ctx.getSharedPreferences("token", 0);
        return pref.getString("token", "null");
    }
}
