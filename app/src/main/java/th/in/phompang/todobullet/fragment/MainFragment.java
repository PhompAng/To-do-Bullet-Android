package th.in.phompang.todobullet.fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.Task;
import th.in.phompang.todobullet.helper.TaskAdapter;
import th.in.phompang.todobullet.helper.SQLiteHandler;
import th.in.phompang.todobullet.helper.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private SQLiteHandler db;
    private SessionManager session;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Activity mActivity;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Task> dataset;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        db = new SQLiteHandler(mActivity);
        session = new SessionManager(mActivity);

        if(!session.isLogin()) {
            logoutUser();
        }

        HashMap<String, String> user = db.getUserDeails();

        String name = user.get("name");
        String email = user.get("email");
        String token = user.get("token");


        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ScaleInAnimationAdapter(new TaskAdapter(getActivity(), initTask()));
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
                Toast.makeText(getActivity(), "Wheeeeee", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    private void addItem() {
        dataset.add(new Task("aaaaa", 1));
        mAdapter.notifyDataSetChanged();
    }


    private List<Task> initTask() {
        dataset = new ArrayList<Task>();

        for (int i = 0; i <= 1; i++) {
            dataset.add(new Task(Integer.toString(i), 1));
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
        pref = mActivity.getSharedPreferences("token", 0);
        editor = pref.edit();

        editor.clear();
        editor.commit();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new LoginFragment().newInstance()).commit();
    }


}
