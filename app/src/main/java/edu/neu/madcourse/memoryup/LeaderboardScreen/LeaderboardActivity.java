package edu.neu.madcourse.memoryup.LeaderboardScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import edu.neu.madcourse.memoryup.Flag;
import edu.neu.madcourse.memoryup.R;
import edu.neu.madcourse.memoryup.UserData;

public class LeaderboardActivity extends AppCompatActivity {

    private final ArrayList<LeaderItem> leaderList = new ArrayList<>();
    private LeaderboardViewAdapter leaderboardViewAdapter;
    private final int levelTotal = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // receive username
        final String username = getIntent().getExtras().getString("Username");
        createRecyclerView(username);
    }


    private void createRecyclerView(String username) {
        RecyclerView leaderboardRecyclerView = findViewById(R.id.leaderboard_recyclerview);
        RecyclerView.LayoutManager leaderboardLayoutManger = new LinearLayoutManager(this);
        leaderboardViewAdapter = new LeaderboardViewAdapter(leaderList, username);

        leaderboardRecyclerView.setAdapter(leaderboardViewAdapter);
        leaderboardRecyclerView.setLayoutManager(leaderboardLayoutManger);

        // load leader(all users) list from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot item_snapshot : snapshot.getChildren()) {
                    UserData data = item_snapshot.getValue(UserData.class);
                    String userMilestone = "";
                    if (data != null) {
                        Log.d("hao-test", String.format("fruit: %d, animal: %d, planet: %d", data.maxFruit, data.maxAnimal, data.maxPlanet));

                        if (data.maxAnimal == levelTotal) {
                            userMilestone += "🐤";
                        }
                        if (data.maxFruit == levelTotal) {
                            userMilestone += "🍉";
                        }
                        if (data.maxPlanet == levelTotal) {
                            userMilestone += "🌍";
                        }

                        leaderList.add(new LeaderItem(String.format("%s %s", Flag.getFlag(data.country), item_snapshot.getKey()), data.maxFruit + data.maxAnimal + data.maxPlanet, 0, userMilestone));

                        Collections.sort(leaderList);

                        for (int i = 0; i < leaderList.size(); i++) {
                            leaderList.get(i).setRank(i + 1);
                            Log.d("Hao-tag", String.format("%s %d %d", leaderList.get(i).getName(), leaderList.get(i).getScore(), leaderList.get(i).getRank()));
                        }
                        leaderboardViewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LeaderboardActivity.this, "Fail to read data " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }
}