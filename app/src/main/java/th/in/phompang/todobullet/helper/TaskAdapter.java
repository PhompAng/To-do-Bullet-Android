package th.in.phompang.todobullet.helper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import th.in.phompang.todobullet.TaskList;
import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.Task;


/**
 * Created by Pichai Sivawat on 17/10/2558.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> mPlayer;
    private Context mContex;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;

        public ViewHolder(View v) {
            super(v);

            mName = (TextView) v.findViewById(R.id.lname);

            RecyclerView recycler_view_list = (RecyclerView) v.findViewById(R.id.recycle_view_list);
            recycler_view_list.setHasFixedSize(true);

            //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
            LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
            recycler_view_list.setLayoutManager(layoutManager);

            RecyclerView.Adapter adapter = new TaskListAdapter(v.getContext(), initLst());
            recycler_view_list.setAdapter(adapter);


        }

        private List<TaskList> initLst() {
            String[] data = new String[] {"test", "test1", "test2", "test3"};

            List<TaskList> dataset = new ArrayList<>();

            for (int i=0;i<4;i++) {
                dataset.add(new TaskList(data[i].toString()));
            }

            return  dataset;
        }
    }

    public TaskAdapter(Context ctx, List<Task> dataset) {
        mPlayer = dataset;
        mContex = ctx;
    }

    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContex).inflate(R.layout.recycle_view_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        Task task = mPlayer.get(position);

        holder.mName.setText(task.getTitle());

    }

    @Override
    public int getItemCount() {
        return mPlayer.size();
    }
}
