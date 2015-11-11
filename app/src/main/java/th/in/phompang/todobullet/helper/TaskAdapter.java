package th.in.phompang.todobullet.helper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import th.in.phompang.todobullet.TaskList;
import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.Task;


/**
 * Created by Pichai Sivawat on 17/10/2558.
 */
public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Task> mTask;
    private Context mContex;

    static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mDes;
        public TextView mDate;

        public TextViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.lname);
            mDes = (TextView) v.findViewById(R.id.ldes);
            mDate = (TextView) v.findViewById(R.id.ldate);
        }
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mDate;

        public RecyclerView.Adapter adapter;
        public RecyclerView recycler_view_list;

        public ArrayList<TaskList> dataset;

        public ListViewHolder(View v) {
            super(v);

            mName = (TextView) v.findViewById(R.id.lname);
            mDate = (TextView) v.findViewById(R.id.ldate);

            recycler_view_list = (RecyclerView) v.findViewById(R.id.recycle_view_list);
            recycler_view_list.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
            recycler_view_list.setLayoutManager(layoutManager);

            adapter = new TaskListAdapter(v.getContext(), init());
            recycler_view_list.setAdapter(adapter);
        }

        public ArrayList<TaskList> init() {
            dataset = new ArrayList<>();
            return dataset;
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public ImageView mImageView;

        public ImageViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.lname);
            mImageView = (ImageView) v.findViewById(R.id.img_view);
        }
    }

    public TaskAdapter(Context ctx, ArrayList<Task> dataset) {
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
                textViewHolder.mDate.setText(task.getDatetime());
                if (task.getDescripton().isEmpty()) {
                    textViewHolder.mDes.setVisibility(View.GONE);
                } else {
                    textViewHolder.mDes.setText(task.getDescripton());
                }
                break;
            case Task.TYPE_LIST:
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                listViewHolder.mName.setText(task.getTitle());
                listViewHolder.mDate.setText(task.getDatetime());

                RecyclerView.Adapter adapter = new TaskListAdapter(mContex, task.getDataset());
                listViewHolder.recycler_view_list.setAdapter(adapter);
                break;
            case Task.TYPE_IMAGE:default:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.mName.setText(task.getTitle());

                Glide.with(mContex).load("http://203.170.193.91:8000/img.jpg").fitCenter().centerCrop().into(imageViewHolder.mImageView);
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
