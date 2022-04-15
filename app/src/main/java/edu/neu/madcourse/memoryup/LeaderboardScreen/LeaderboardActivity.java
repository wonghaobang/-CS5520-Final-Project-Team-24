package edu.neu.madcourse.memoryup.LeaderboardScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import edu.neu.madcourse.memoryup.R;
import edu.neu.madcourse.memoryup.UserData;

public class LeaderboardActivity extends AppCompatActivity {

    private final ArrayList<LeaderItem> leaderList = new ArrayList<>();
    private LeaderboardViewAdapter leaderboardViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        createRecyclerView();
    }


    private void createRecyclerView() {
        RecyclerView leaderboardRecyclerView = findViewById(R.id.leaderboard_recyclerview);
        RecyclerView.LayoutManager leaderboardLayoutManger = new LinearLayoutManager(this);
        leaderboardViewAdapter = new LeaderboardViewAdapter(leaderList);

        leaderboardRecyclerView.setAdapter(leaderboardViewAdapter);
        leaderboardRecyclerView.setLayoutManager(leaderboardLayoutManger);

        // load leader(all users) list from firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData data = snapshot.getValue(UserData.class);
                // sort array based on score
                // populate leaderList with name, score, rank (rank will just be index)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LeaderboardActivity.this, "Fail to read data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}