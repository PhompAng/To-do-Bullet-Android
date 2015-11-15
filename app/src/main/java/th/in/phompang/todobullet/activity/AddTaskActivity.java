package th.in.phompang.todobullet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.TaskList;
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

        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle extra = getIntent().getExtras();
        switch (extra.getInt("mode")) {
            case 0:
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putAll(extra);

        switch (extra.getInt("type")) {
            case 0:
                NewTaskTextFragment taskTextFragment = NewTaskTextFragment.newInstance();
                taskTextFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.flContent, taskTextFragment).commit();
                break;
            case 1:
                NewTaskListFragment taskListFragment = NewTaskListFragment.newInstance();
                taskListFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.flContent, taskListFragment).commit();
                break;
        }



    }

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
    public void onNewTaskText(String title, String description, String datetime, int type) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("datetime", datetime);
        intent.putExtra("type", type);
        intent.putExtra("mode", 0);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNewTaskText(String title, String description, String datetime, int type, int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("datetime", datetime);
        intent.putExtra("type", type);
        intent.putExtra("position", position);
        intent.putExtra("mode", 1);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNewTaskList(String title, ArrayList<TaskList> lst, String datetime, int type) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        intent.putParcelableArrayListExtra("list", lst);
        intent.putExtra("datetime", datetime);
        intent.putExtra("type", type);
        intent.putExtra("mode", 0);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNewTaskList(String title, ArrayList<TaskList> lst, String datetime, int type, int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        intent.putParcelableArrayListExtra("list", lst);
        intent.putExtra("datetime", datetime);
        intent.putExtra("type", type);
        intent.putExtra("position", position);
        intent.putExtra("mode", 1);
        startActivity(intent);
        finish();
    }
}
