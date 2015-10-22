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
public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Task> mTask;
    private Context mContex;

    static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;

        public TextViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.lname);
        }
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;

        public ListViewHolder(View v) {
            super(v);

            mName = (TextView) v.findViewById(R.id.lname);

            RecyclerView recycler_view_list = (RecyclerView) v.findViewById(R.id.recycle_view_list);
            recycler_view_list.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
            recycler_view_list.setLayoutManager(layoutManager);

            RecyclerView.Adapter adapter = new TaskListAdapter(v.getContext(), initLst());
            recycler_view_list.setAdapter(adapter);
        }

        private List<TaskList> initLst() {
            String[] data = new String[] {"test", "test1", "test2", "test3", "test4", "test5"};

            List<TaskList> dataset = new ArrayList<>();

            for (int i=0;i<data.length;i++) {
                dataset.add(new TaskList(data[i]));
            }

            return  dataset;
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;

        public ImageViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.lname);
        }
    }

    public TaskAdapter(Context ctx, List<Task> dataset) {
        mTask = dataset;
        mContex = ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContex);

        switch (viewType) {
            case Task.TYPE_TEXT:
                View textView = inflater.inflate(R.layout.recycle_view_text, parent, false);
                return new TextViewHolder(textView);
            case Task.TYPE_LIST:
                View listView = inflater.inflate(R.layout.recycle_view_list, parent, false);
                return new ListViewHolder(listView);
            case Task.TYPE_IMAGE:default:
                View imageView = inflater.inflate(R.layout.recycle_view_image, parent, false);
                return new ImageViewHolder(imageView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Task task = mTask.get(position);

        switch (holder.getItemViewType()) {
            case Task.TYPE_TEXT:
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.mName.setText(task.getTitle());
                break;
            case Task.TYPE_LIST:
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                listViewHolder.mName.setText(task.getTitle());
                break;
            case Task.TYPE_IMAGE:default:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.mName.setText(task.getTitle());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTask.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mTask.get(position).getType();
    }
}
