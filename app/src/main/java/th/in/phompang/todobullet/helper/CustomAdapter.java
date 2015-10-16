package th.in.phompang.todobullet.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import th.in.phompang.todobullet.R;
import th.in.phompang.todobullet.Player;

/**
 * Created by Pichai Sivawat on 17/10/2558.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Player> mPlayer;
    private Context mContex;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mClub;

        public ViewHolder(View v) {
            super(v);

            mName = (TextView) v.findViewById(R.id.lname);
            mClub = (TextView) v.findViewById(R.id.lclub);
        }
    }

    public CustomAdapter(Context ctx, List<Player> dataset) {
        mPlayer = dataset;
        mContex = ctx;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContex).inflate(R.layout.recycle_view_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        Player player = mPlayer.get(position);

        holder.mName.setText(player.getName());
        holder.mClub.setText(player.getClub());

    }

    @Override
    public int getItemCount() {
        return mPlayer.size();
    }
}
