package edu.neu.madcourse.memoryup.LeaderboardScreen;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import edu.neu.madcourse.memoryup.Flag;
import edu.neu.madcourse.memoryup.R;

public class LeaderboardViewAdapter extends RecyclerView.Adapter<LeaderboardViewHolder> {
    private final ArrayList<LeaderItem> leaderList;
    private final String username;


    public LeaderboardViewAdapter(ArrayList<LeaderItem> leaderList, String username) {
        this.leaderList = leaderList;
        this.username = username;
    }


    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.leader_item, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        LeaderItem currentItem = leaderList.get(position);
        holder.rank.setText("#".concat(String.valueOf(currentItem.getRank())));
        holder.name.setText(currentItem.getName());
        holder.score.setText(String.valueOf(currentItem.getScore()));
        holder.milestone.setText(currentItem.getMilestone());

        holder.rank.setTextColor(Color.BLACK);
        holder.name.setTextColor(Color.BLACK);
        holder.score.setTextColor(Color.BLACK);
        holder.milestone.setTextColor(Color.BLACK);

        if (currentItem.getName().split(" ")[1].equals(username)) {
            holder.itemView.setBackgroundColor(Color.rgb(173,216,230));
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return leaderList.size();
    }
}
