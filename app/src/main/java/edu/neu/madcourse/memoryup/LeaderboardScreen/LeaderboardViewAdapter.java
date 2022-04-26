package edu.neu.madcourse.memoryup.LeaderboardScreen;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import edu.neu.madcourse.memoryup.R;
import edu.neu.madcourse.memoryup.UserData;

public class LeaderboardViewAdapter extends RecyclerView.Adapter<LeaderboardViewHolder> {
    private final ArrayList<LeaderItem> leaderList;
    private final String username;
    private String myMilestones = "";
    private final int levelTotal = 4;

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users").child(currentItem.getName());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserData data = snapshot.getValue(UserData.class);
                    Log.d("basketball", String.format("user: %s, animal: %d, fruit: %d, planet: %d", snapshot.getKey(), data.maxAnimal, data.maxFruit, data.maxPlanet));
                    if (data.maxAnimal == levelTotal) {
                       myMilestones += "🐤";
                       holder.milestone.setText(myMilestones);
                        Log.d("basketball", "maxAnimal is 6");
                    }
                    if (data.maxFruit == levelTotal) {
                        myMilestones += "🍉";
                        holder.milestone.setText(myMilestones);
                        Log.d("basketball", "maxFruit is 6");
                    }
                    if (data.maxPlanet == levelTotal) {
                        myMilestones += "🌍";
                        holder.milestone.setText(myMilestones);
                        Log.d("basketball", "maxPlanet is 6");
                    }
                    if (data.maxFruit != levelTotal && data.maxAnimal != levelTotal && data.maxPlanet != levelTotal) {
                        holder.milestone.setText("");
                    }
                    myMilestones = "";

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

//        holder.milestone.setText("🐤🍉🌍");
//        holder.milestone.setText(myMilestones);
        holder.milestone.setTextColor(Color.WHITE);

        if (currentItem.getName().equals(username)) {
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
