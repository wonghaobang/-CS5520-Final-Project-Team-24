package edu.neu.madcourse.memoryup.LeaderboardScreen;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import edu.neu.madcourse.memoryup.R;

public class LeaderboardViewHolder extends RecyclerView.ViewHolder {
    public TextView rank;
    public TextView name;
    public TextView score;
    public TextView milestone;

    public LeaderboardViewHolder(View itemView) {
        super(itemView);
        rank = itemView.findViewById(R.id.rank_textView);
        name = itemView.findViewById(R.id.name_textView);
        score = itemView.findViewById(R.id.score_textView);
        milestone = itemView.findViewById(R.id.milestone_textView);
    }
}
