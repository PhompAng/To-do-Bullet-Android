package th.in.phompang.todobullet.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.Task;
import th.in.phompang.todobullet.activity.AddTaskActivity;
import th.in.phompang.todobullet.helper.TaskAdapter;
import th.in.phompang.todobullet.helper.SQLiteHandler;
import th.in.phompang.todobullet.helper.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    public static final int DIALOG_FRAGMENT = 1;

    private SQLiteHandler db;
    private SessionManager session;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Activity mActivity;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Task> dataset;

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        db = new SQLiteHandler(mActivity);
        session = new SessionManager(mActivity);

        if(!session.isLogin()) {
            logoutUser();
        }

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ScaleInAnimationAdapter(new TaskAdapter(getActivity(), initTask()));
        mRecyclerView.setAdapter(mAdapter);

        final View actionB = v.findViewById(R.id.new_task_text);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewTaskDialog();
            }
        });

        if (getArguments().getString("title") != null) {
            addItem(getArguments().getString("title"), "", 0);
        }

        return v;
    }

    private void showNewTaskDialog() {
        Intent intent = new Intent(getContext(), AddTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DIALOG_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    addItem(data.getStringExtra("name"), "", 0);
                } else if (requestCode == Activity.RESULT_CANCELED) {
                    // nothing to do here;
                }
                break;
        }
    }

    private void addItem(String title, String description, int type) {
        dataset.add(new Task(title, description, Task.TYPE_TEXT));
        db.addTask(title, description, type, "");
        mAdapter.notifyDataSetChanged();
    }


    private List<Task> initTask() {
        dataset = new ArrayList<Task>();
        ArrayList<HashMap<String, String>> tasks = db.getTaskDeails();

        for (HashMap<String, String> task: tasks) {
            dataset.add(new Task(task.get("title"), task.get("description"), Integer.parseInt(task.get("type"))));
        }

        return dataset;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();
        db.deleteTask();
        pref = mActivity.getSharedPreferences("token", 0);
        editor = pref.edit();

        editor.clear();
        editor.commit();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new LoginFragment().newInstance()).commit();
    }
}
