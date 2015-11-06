package th.in.phompang.todobullet.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.fragment.NewTaskListFragment;
import th.in.phompang.todobullet.fragment.NewTaskTextFragment;

public class AddTaskActivity extends AppCompatActivity implements NewTaskTextFragment.OnSaveSelectedListener, NewTaskListFragment.OnSaveSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        toolbar.setTitle("New Text Task");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);
//
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
//        collapsingToolbarLayout.setTitle(" ");

        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle extra = getIntent().getExtras();
        switch (extra.getInt("type")) {
            case 0:
                fragmentManager.beginTransaction().replace(R.id.flContent, new NewTaskTextFragment().newInstance()).commit();
                break;
            case 1:
                fragmentManager.beginTransaction().replace(R.id.flContent, new NewTaskListFragment().newInstance()).commit();
        }


    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_add_task, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNewTaskText(String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNewTaskList(Uri uri) {

    }
}
