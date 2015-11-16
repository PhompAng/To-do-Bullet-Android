package th.in.phompang.todobullet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;

import th.in.phompang.todobullet.Datetime;
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

    public static final int DATEPICKER_FRAGMENT = 1;
    public static final int TIMEPICKER_FRAGMENT = 2;

    private TaskListEditAdapter mAdapter;
    private TextView title;
    private Button button;
    private Spinner mDate;
    private Spinner mTime;
    private ArrayAdapter<String> dateAdapter;
    private ArrayAdapter<String> timeAdapter;

    private ArrayList<TaskList> dataset;
    public Datetime datetime;
    public int position = -1;
    public int mode = 0;

    private OnSaveSelectedListener mCallback;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewTaskListFragment.
     */
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
        datetime = new Datetime();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_task_list, container, false);

        title = (TextView) v.findViewById(R.id.new_task_list_title);
        mDate = (Spinner) v.findViewById(R.id.date);
        mTime = (Spinner) v.findViewById(R.id.time);

        dateAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, datetime.date_data);
        mDate.setAdapter(dateAdapter);
        timeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, datetime.time_data);
        mTime.setAdapter(timeAdapter);

        mDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == datetime.date_data.size() - 1) {
                    DialogFragment datefragment = DatePickerFragment.newInstance();
                    datefragment.setTargetFragment(NewTaskListFragment.this, DATEPICKER_FRAGMENT);
                    datefragment.show(getFragmentManager().beginTransaction(), "datepicker");
                } else {
                    datetime.pickDate(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == datetime.time_data.size() - 1) {
                    DialogFragment timefragment = TimePickerFragment.newInstance();
                    timefragment.setTargetFragment(NewTaskListFragment.this, TIMEPICKER_FRAGMENT);
                    timefragment.show(getFragmentManager().beginTransaction(), "timepicker");
                } else {
                    datetime.pickTime(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.new_task_list_list);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), android.support.v7.widget.LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        Bundle arg = getArguments();
        title.setText(arg.getString("title"));
        dataset = new ArrayList<>();

        if (arg.getParcelableArrayList("list") != null) {
            dataset = arg.getParcelableArrayList("list");
        } else {
            dataset = initList();
        }

        mAdapter = new TaskListEditAdapter(getActivity(), dataset);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new TaskListEditAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                mAdapter.removeAt(position);
            }
        });
        mAdapter.notifyDataSetChanged();

        button = (Button) v.findViewById(R.id.add_new_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataset.add(new TaskList(""));
                mAdapter.notifyDataSetChanged();
            }
        });

        position = arg.getInt("position");
        mode = arg.getInt("mode");

        return v;
    }

    public ArrayList<TaskList> initList() {
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
        void onNewTaskList(String title, ArrayList<TaskList> lst, String datetime, int type);
        void onNewTaskList(String title, ArrayList<TaskList> lst, String datetime, int type, int position);
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
            switch (mode) {
                case 0:
                    mCallback.onNewTaskList(title.getText().toString(), dataset, datetime.getDatetime(), 1);
                    break;
                case 1:
                    mCallback.onNewTaskList(title.getText().toString(), dataset, datetime.getDatetime(), 1, position);
                    break;
            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case DATEPICKER_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    String year = Integer.toString(intent.getIntExtra("year", 0));
                    String month = String.format("%02d", intent.getIntExtra("month", 0));
                    String date = String.format("%02d", intent.getIntExtra("date", 0));
                    datetime.date_data.set(datetime.date_data.size() - 1, year + "-" + month + "-" + date);
                    dateAdapter.notifyDataSetChanged();
                    datetime.setDate(year + "-" + month + "-" + date);
                }
                break;
            case TIMEPICKER_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    String hour = String.format("%02d", intent.getIntExtra("hour", 0));
                    String minute = String.format("%02d", intent.getIntExtra("minute", 0));
                    datetime.time_data.set(datetime.time_data.size() - 1, hour + ":" + minute);
                    timeAdapter.notifyDataSetChanged();
                    datetime.setTime(hour + ":" + minute + ":00");
                }
        }
    }

}
