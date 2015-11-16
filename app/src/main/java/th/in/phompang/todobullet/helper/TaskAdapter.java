package th.in.phompang.todobullet.helper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import th.in.phompang.todobullet.TaskList;
import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.Task;


/**
 * Created by Pichai Sivawat on 17/10/2558.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private SQLiteHandler db;

    private SparseBooleanArray selectedItems;
    private ArrayList<Task> mTask;
    private Context mContext;

    private ViewHolder.ClickListener clickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        View selectedOverlay;

        private ClickListener listener;

        public ViewHolder(View view) {
            super(view);
            selectedOverlay = view.findViewById(R.id.selected_overlay);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return listener != null && listener.onItemLongClicked(getAdapterPosition());
        }

        public interface ClickListener {
            void onItemClicked(int position);
            boolean onItemLongClicked(int position);
        }
    }

    static class TextViewHolder extends ViewHolder {
        public TextView mName;
        public TextView mDes;
        public TextView mDate;

        public TextViewHolder(View v, ClickListener listener) {
            super(v);
            mName = (TextView) v.findViewById(R.id.lname);
            mDes = (TextView) v.findViewById(R.id.ldes);
            mDate = (TextView) v.findViewById(R.id.ldate);

            super.listener = listener;

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }
    }

    static class ListViewHolder extends ViewHolder {
        public TextView mName;
        public TextView mDate;

        public RecyclerView.Adapter adapter;
        public RecyclerView recycler_view_list;

        public ArrayList<TaskList> dataset;

        public ListViewHolder(View v, ClickListener listener) {
            super(v);

            mName = (TextView) v.findViewById(R.id.lname);
            mDate = (TextView) v.findViewById(R.id.ldate);

            recycler_view_list = (RecyclerView) v.findViewById(R.id.recycle_view_list);
            recycler_view_list.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
            recycler_view_list.setLayoutManager(layoutManager);

            adapter = new TaskListAdapter(v.getContext(), init());
            recycler_view_list.setAdapter(adapter);

            super.listener = listener;

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        public ArrayList<TaskList> init() {
            dataset = new ArrayList<>();
            return dataset;
        }
    }

    static class ImageViewHolder extends ViewHolder {
        public TextView mName;
        public ImageView mImageView;
        public TextView mDate;

        public ImageViewHolder(View v, ClickListener listener) {
            super(v);
            mName = (TextView) v.findViewById(R.id.lname);
            mImageView = (ImageView) v.findViewById(R.id.img_view);
            mDate = (TextView) v.findViewById(R.id.ldate);

            super.listener = listener;

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }
    }

    public TaskAdapter(Context ctx, ArrayList<Task> dataset, ViewHolder.ClickListener listener) {
        mTask = dataset;
        mContext = ctx;
        selectedItems = new SparseBooleanArray();
        this.clickListener = listener;
        db = new SQLiteHandler(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (viewType) {
            case Task.TYPE_TEXT:
                View textView = inflater.inflate(R.layout.recycle_view_text, parent, false);
                return new TextViewHolder(textView, clickListener);
            case Task.TYPE_LIST:
                View listView = inflater.inflate(R.layout.recycle_view_list, parent, false);
                return new ListViewHolder(listView, clickListener);
            case Task.TYPE_IMAGE:default:
                View imageView = inflater.inflate(R.layout.recycle_view_image, parent, false);
                return new ImageViewHolder(imageView, clickListener);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

                RecyclerView.Adapter adapter = new TaskListAdapter(mContext, task.getDataset());
                listViewHolder.recycler_view_list.setAdapter(adapter);
                break;
            case Task.TYPE_IMAGE:default:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.mName.setText(task.getTitle());
                imageViewHolder.mDate.setText(task.getDatetime());

                Glide.with(mContext).loadFromMediaStore(task.getImage()).fitCenter().centerCrop().into(imageViewHolder.mImageView);
                break;
        }
        holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mTask.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mTask.get(position).getType();
    }

    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeAt(int position) {
        db.deleteTask((int) mTask.get(position).getId());
        mTask.remove(position);
        notifyItemRemoved(position);
    }
}
