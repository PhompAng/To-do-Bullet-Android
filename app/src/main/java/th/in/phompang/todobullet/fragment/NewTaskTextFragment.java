package th.in.phompang.todobullet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import th.in.phompang.todobullet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTaskTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTaskTextFragment extends Fragment {

    public static final int DATEPICKER_FRAGMENT = 1;
    public static final int TIMEPICKER_FRAGMENT = 2;

    public String date = "";
    public String time = "";
    public int position = -1;
    public int mode = 0;

    OnSaveSelectedListener mCallback;

    public interface OnSaveSelectedListener {
        public void onNewTaskText(String title, String description, String datetime, int type);
        public void onNewTaskText(String title, String description, String datetime, int type, int position);
    }

    private ArrayList<String> date_data;
    private ArrayList<String> time_data;

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
    // TODO: Rename and change types and number of parameters
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
        View v = inflater.inflate(R.layout.fragment_new_task_text, container, false);

        title = (EditText) v.findViewById(R.id.new_task_text_title);
        description = (EditText) v.findViewById(R.id.new_task_text_description);
        mDate = (Spinner) v.findViewById(R.id.date);
        mTime = (Spinner) v.findViewById(R.id.time);

        dateAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, initDateArray());
        mDate.setAdapter(dateAdapter);
        timeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, initTimeArray());
        mTime.setAdapter(timeAdapter);

        mDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == date_data.size() - 1) {
                    DialogFragment datefragment = DatePickerFragment.newInstance();
                    datefragment.setTargetFragment(NewTaskTextFragment.this, DATEPICKER_FRAGMENT);
                    datefragment.show(getFragmentManager().beginTransaction(), "datepicker");
                } else {
                    pickDate(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == time_data.size() - 1) {
                    DialogFragment timefragment = TimePickerFragment.newInstance();
                    timefragment.setTargetFragment(NewTaskTextFragment.this, TIMEPICKER_FRAGMENT);
                    timefragment.show(getFragmentManager().beginTransaction(), "timepicker");
                } else {
                    pickTime(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Bundle arg = getArguments();
        title.setText(arg.getString("title"));
        description.setText(arg.getString("description"));
        position = arg.getInt("position");
        mode = arg.getInt("mode");
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

    public ArrayList<String> initDateArray() {
        date_data = new ArrayList<>();
        date_data.add("Today");
        date_data.add("Tomorrow");
        date_data.add("Next week");
        date_data.add("Pick a date...");

        return date_data;
    }

    public ArrayList<String> initTimeArray() {
        time_data = new ArrayList<>();
        time_data.add("Morning");
        time_data.add("Afternoon");
        time_data.add("Evening");
        time_data.add("Night");
        time_data.add("Pick a time...");

        return time_data;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void pickTime(int position) {
        switch (position) {
            case 0:
                this.time = "09:00:00";
                break;
            case 1:
                this.time = "13:00:00";
                break;
            case 2:
                this.time = "17:00:00";
                break;
            case 3:
                this.time = "20:00:00";
                break;
            default:
                break;
        }
        Toast.makeText(getContext(), this.time, Toast.LENGTH_LONG).show();
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void pickDate(int position) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        switch (position) {
            case 0:
                this.date = Integer.toString(year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", date);
                break;
            case 1:
                c.add(Calendar.DAY_OF_YEAR, 1);
                date = c.get(Calendar.DATE);
                this.date = Integer.toString(year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", date);
                break;
            case 2:
                int i = c.get(Calendar.WEEK_OF_MONTH);
                c.set(Calendar.WEEK_OF_MONTH, ++i);
                date = c.get(Calendar.DATE);
                this.date = Integer.toString(year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", date);
            default:
                break;
        }
        Toast.makeText(getContext(), this.date, Toast.LENGTH_LONG).show();
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
                    mCallback.onNewTaskText(title.getText().toString(), description.getText().toString(), getDate() + " " + getTime(), 0);
                    break;
                case 1:
                    mCallback.onNewTaskText(title.getText().toString(), description.getText().toString(), getDate() + " " + getTime(), 0, position);
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
                    //Toast.makeText(getContext(), year+month+date, Toast.LENGTH_LONG).show();
                    date_data.set(date_data.size() - 1, year + "-" + month + "-" + date);
                    dateAdapter.notifyDataSetChanged();
                    setDate(year + "-" + month + "-" + date);
                }
                break;
            case TIMEPICKER_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    String hour = String.format("%02d", intent.getIntExtra("hour", 0));
                    String minute = String.format("%02d", intent.getIntExtra("minute", 0));
                    time_data.set(time_data.size() - 1, hour + ":" + minute);
                    timeAdapter.notifyDataSetChanged();
                    setTime(hour + ":" + minute + ":00");
                }
        }
    }
}
