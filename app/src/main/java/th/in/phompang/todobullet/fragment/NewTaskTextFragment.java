package th.in.phompang.todobullet.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import th.in.phompang.todobullet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTaskTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTaskTextFragment extends Fragment {

    OnSaveSelectedListener mCallback;

    public interface OnSaveSelectedListener {
        public void onNewTaskText(String title, String description, int type);
    }

    private EditText title;
    private EditText description;

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
            mCallback.onNewTaskText(title.getText().toString(), description.getText().toString(), 0);
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
