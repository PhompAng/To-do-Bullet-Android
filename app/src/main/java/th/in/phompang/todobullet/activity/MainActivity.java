package th.in.phompang.todobullet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.helper.SQLiteHandler;
import th.in.phompang.todobullet.helper.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SQLiteHandler db;
    private SessionManager session;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if(!session.isLogin()) {
            logoutUser();
        }

        HashMap<String, String> user = db.getUserDeails();

        String name = user.get("name");
        String email = user.get("email");
        String token = user.get("token");

        TextView textView = (TextView) findViewById(R.id.text1);

        textView.setText(token);

        Button logoutBtn = (Button) findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    public void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();
        pref = getSharedPreferences("token", 0);
        editor = pref.edit();

        editor.clear();
        editor.commit();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_gettoken) {
            Intent gettoken = new Intent(this, GetTokenActivity.class);
            startActivity(gettoken);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
