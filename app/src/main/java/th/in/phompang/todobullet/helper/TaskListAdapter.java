package th.in.phompang.todobullet.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import th.in.phompang.todobullet.TaskList;
import th.in.phompang.todobullet.R;

/**
 * Created by Pichai Sivawat on 20/10/2558.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<TaskList> mList;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxt;

        public ViewHolder(View v) {
            super(v);

            mTxt = (TextView) v.findViewById(R.id.lst_txt);
        }
    }

    public TaskListAdapter(Context ctx, List<TaskList> taskList) {
        mList = taskList;
        mContext = ctx;
    }

    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.task_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.ViewHolder holder, int position) {
        TaskList taskList = mList.get(position);

        holder.mTxt.setText(taskList.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
