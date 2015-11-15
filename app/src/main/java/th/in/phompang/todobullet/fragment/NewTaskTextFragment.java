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

    public String datetime = "";
    public int position = -1;
    public int mode = 0;

    OnSaveSelectedListener mCallback;

    public interface OnSaveSelectedListener {
        public void onNewTaskText(String title, String description, String datetime, int type);
        public void onNewTaskText(String title, String description, String datetime, int type, int position);
    }

    private ArrayList<String> date_data;

    private EditText title;
    private EditText description;
    private Spinner date;
    private ArrayAdapter<String> arrayAdapter;

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
        date = (Spinner) v.findViewById(R.id.date);

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, initdateArray());
        date.setAdapter(arrayAdapter);

        date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == date_data.size() - 1) {
                    DialogFragment datefragment = DatePickerFragment.newInstance();
                    datefragment.setTargetFragment(NewTaskTextFragment.this, DATEPICKER_FRAGMENT);
                    datefragment.show(getFragmentManager().beginTransaction(), "datepicker");
                } else {
                    setDatetime(position);
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

    public ArrayList<String> initdateArray() {
        date_data = new ArrayList<>();
        date_data.add("Today");
        date_data.add("Tomorrow");
        date_data.add("Next week");
        date_data.add("Pick a date...");

        return date_data;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDateTime(String datetime) {
        this.datetime = datetime;
    }

    public void setDatetime(int position) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        switch (position) {
            case 0:
                this.datetime = Integer.toString(year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", date);
                break;
            case 1:
                c.add(Calendar.DAY_OF_YEAR, 1);
                date = c.get(Calendar.DATE);
                this.datetime = Integer.toString(year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", date);
                break;
            case 2:
                int i = c.get(Calendar.WEEK_OF_MONTH);
                c.set(Calendar.WEEK_OF_MONTH, ++i);
                date = c.get(Calendar.DATE);
                this.datetime = Integer.toString(year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", date);
            default:
                break;
        }
        Toast.makeText(getContext(), datetime, Toast.LENGTH_LONG).show();
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
                    mCallback.onNewTaskText(title.getText().toString(), description.getText().toString(), getDatetime(), 0);
                    break;
                case 1:
                    mCallback.onNewTaskText(title.getText().toString(), description.getText().toString(), getDatetime(), 0, position);
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
                    arrayAdapter.notifyDataSetChanged();
                    setDateTime(year + "-" + month + "-" + date);
                }
        }
    }
}
