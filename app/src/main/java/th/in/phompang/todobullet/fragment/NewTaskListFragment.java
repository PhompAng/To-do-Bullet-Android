package th.in.phompang.todobullet.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.TaskList;
import th.in.phompang.todobullet.helper.TaskListEditAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSaveSelectedListener} interface
 * to handle interaction events.
 * Use the {@link NewTaskListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTaskListFragment extends Fragment {

    private RecyclerView.Adapter mAdapter;
    private TextView title;
    private Button button;

    private ArrayList<TaskList> dataset;

    private OnSaveSelectedListener mCallback;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewTaskListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewTaskListFragment newInstance() {
        NewTaskListFragment fragment = new NewTaskListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewTaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_task_list, container, false);

        title = (TextView) v.findViewById(R.id.new_task_list_title);

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.new_task_list_list);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), android.support.v7.widget.LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ScaleInAnimationAdapter(new TaskListEditAdapter(getActivity(), initList()));
        mRecyclerView.setAdapter(mAdapter);

        button = (Button) v.findViewById(R.id.add_new_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataset.add(new TaskList(""));
                mAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    public void addList(String name) {
        dataset.add(new TaskList(name));
        mAdapter.notifyDataSetChanged();
    }

    public ArrayList<TaskList> initList() {
        dataset = new ArrayList<TaskList>();
        dataset.add(new TaskList(""));

        return dataset;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnSaveSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSaveSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface OnSaveSelectedListener {
        // TODO: Update argument type and name
        public void onNewTaskList(String title, ArrayList<TaskList> lst, int type);
    }

    public void validate() {
        title.setError(null);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(title.getText().toString())) {
            title.setError("Title is Empty");
            focusView = title;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mCallback.onNewTaskList(title.getText().toString(), dataset, 1);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                validate();
                return true;
            case R.id.action_palette:
                //TODO change task color
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
