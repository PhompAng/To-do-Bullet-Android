package th.in.phompang.todobullet.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.TaskList;

/**
 * Created by Pichai Sivawat on 20/10/2558.
 */
public class TaskListEditAdapter extends RecyclerView.Adapter<TaskListEditAdapter.ViewHolder> {

    private ArrayList<TaskList> mDataset;
    private Context mContext;

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public EditText mTxt;
        public ImageView mImageView;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View v, MyCustomEditTextListener myCustomEditTextListener) {
            super(v);

            mImageView = (ImageView) v.findViewById(R.id.list_remove);

            this.mTxt = (EditText) v.findViewById(R.id.new_task_list_list_item);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.mTxt.addTextChangedListener(myCustomEditTextListener);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    public TaskListEditAdapter(Context ctx, ArrayList<TaskList> taskList) {
        mDataset = taskList;
        mContext = ctx;
    }

    @Override
    public TaskListEditAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycle_view_new_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v, new MyCustomEditTextListener());

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskListEditAdapter.ViewHolder holder, final int position) {

        holder.myCustomEditTextListener.updatePosition(position);
        holder.mTxt.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void removeAt(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
        if (mDataset.size() == 0) {
            mDataset.add(new TaskList(""));
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mDataset.set(position, new TaskList(charSequence.toString()));
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}
