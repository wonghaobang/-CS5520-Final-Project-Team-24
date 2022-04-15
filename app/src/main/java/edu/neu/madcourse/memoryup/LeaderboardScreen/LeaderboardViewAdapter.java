package edu.neu.madcourse.memoryup.LeaderboardScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import edu.neu.madcourse.memoryup.R;

public class LeaderboardViewAdapter extends RecyclerView.Adapter<LeaderboardViewHolder> {
    private final ArrayList<LeaderItem> leaderList;

    public LeaderboardViewAdapter(ArrayList<LeaderItem> leaderList) {
        this.leaderList = leaderList;
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
        holder.rank.setText(String.valueOf(currentItem.getRank()));
        holder.name.setText(currentItem.getName());
        holder.score.setText(String.valueOf(currentItem.getScore()));
    }

    @Override
    public int getItemCount() {
        return leaderList.size();
    }
}
