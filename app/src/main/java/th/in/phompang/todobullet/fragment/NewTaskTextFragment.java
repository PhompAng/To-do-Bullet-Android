package th.in.phompang.todobullet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import th.in.phompang.todobullet.Datetime;
import th.in.phompang.todobullet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTaskTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTaskTextFragment extends Fragment {

    public static final int DATEPICKER_FRAGMENT = 1;
    public static final int TIMEPICKER_FRAGMENT = 2;

    public Datetime datetime;
    public int position = -1;
    public int mode = 0;

    OnSaveSelectedListener mCallback;

    public interface OnSaveSelectedListener {
        void onNewTaskText(String title, String description, String datetime, int type);
        void onNewTaskText(String title, String description, String datetime, int type, int position);
    }

    private EditText title;
    private EditText description;
    private Spinner mDate;
    private Spinner mTime;
    private ArrayAdapter<String> dateAdapter;
    private ArrayAdapter<String> timeAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewTaskTextFragment.
     */
    public static NewTaskTextFragment newInstance() {
        NewTaskTextFragment fragment = new NewTaskTextFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewTaskTextFragment() {
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
        Bundle arg = getArguments();
        mode = arg.getInt("mode");

        View v = inflater.inflate(R.layout.fragment_new_task_text, container, false);

        title = (EditText) v.findViewById(R.id.new_task_text_title);
        description = (EditText) v.findViewById(R.id.new_task_text_description);
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
                    datefragment.setTargetFragment(NewTaskTextFragment.this, DATEPICKER_FRAGMENT);
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
                    timefragment.setTargetFragment(NewTaskTextFragment.this, TIMEPICKER_FRAGMENT);
                    timefragment.show(getFragmentManager().beginTransaction(), "timepicker");
                } else {
                    datetime.pickTime(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        title.setText(arg.getString("title"));
        description.setText(arg.getString("description"));
        position = arg.getInt("position");
        if (arg.getString("datetime") != null) {
            String[] date = arg.getString("datetime").split("\\s+");
            datetime.setDate(date[0]);
            datetime.setTime(date[1]);
            datetime.date_data.add(datetime.date_data.size() - 1, datetime.getDate());
            datetime.time_data.add(datetime.time_data.size() - 1, datetime.getTime());
            mDate.setSelection(datetime.date_data.size()-2);
            mTime.setSelection(datetime.time_data.size()-2);
        }
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnSaveSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
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
                    mCallback.onNewTaskText(title.getText().toString(), description.getText().toString(), datetime.getDatetime(), 0);
                    break;
                case 1:
                    mCallback.onNewTaskText(title.getText().toString(), description.getText().toString(), datetime.getDatetime(), 0, position);
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
