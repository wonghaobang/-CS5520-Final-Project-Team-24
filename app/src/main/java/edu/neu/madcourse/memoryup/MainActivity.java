package edu.neu.madcourse.memoryup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.neu.madcourse.memoryup.LeaderboardScreen.LeaderboardActivity;
import edu.neu.madcourse.memoryup.LevelSelectorScreen.LevelSelectorActivity;

public class MainActivity extends AppCompatActivity {
    // database
    private DatabaseReference reference;
    private String username = null;
    private UserData userData = null;

    // audio controls
    private boolean playMusic = true;
    private Intent musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect to firebase and set user data
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        musicService = new Intent(this, BackgroundMusicService.class);

        if (savedInstanceState != null && savedInstanceState.containsKey("username")) {
            username = savedInstanceState.getString("username");
            playMusic = savedInstanceState.getInt("playMusic") == 1;
        }

        // prompt username if not set
        if (username == null)
            promptLogin();

        SwitchCompat musicSwitch = (SwitchCompat) findViewById(R.id.musicSwitch);
        musicSwitch.setOnCheckedChangeListener((compoundButton, b) -> toggleMusic(b));
        musicSwitch.setChecked(playMusic);
    }

    // don't prompt username on orientation changes
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (username != null) {
            outState.putString("username", username);
            outState.putInt("playMusic", (playMusic ? 0 : 1));
        }
        super.onSaveInstanceState(outState);
    }

    // request username entry
    private void promptLogin() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_login, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();

        EditText etUsername = dialogView.findViewById(R.id.editTextUsername);
        Button logInButton = dialogView.findViewById(R.id.buttonLogIn);
        Button signUpButton = dialogView.findViewById(R.id.buttonSignUp);

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                logInButton.setEnabled(!etUsername.getText().toString().trim().isEmpty());
                signUpButton.setEnabled(!etUsername.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        logInButton.setOnClickListener(view ->
                addFirebaseListener(etUsername.getText().toString(), true, view, dialog));
        signUpButton.setOnClickListener(view ->
                addFirebaseListener(etUsername.getText().toString(), false, view, dialog));

        dialog.show();
    }

    // connect given username to firebase
    private void addFirebaseListener(String etUsername, boolean logIn, View view, AlertDialog dialog) {
        // create a new user if name does not exist
        reference.child(etUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (logIn) {
                        username = etUsername;
                        dialog.dismiss();
                    } else {
                        Snackbar.make(view, R.string.signup_failure, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (logIn) {
                        Snackbar.make(view, R.string.login_failure, Snackbar.LENGTH_SHORT).show();
                    } else {
                        // TODO: Set to actual value
//                        reference.child(etUsername).setValue("");
                        userData = new UserData(etUsername, 20);
                        reference.child(etUsername).setValue(userData);

                        username = etUsername;
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to read data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // start background music
    private void toggleMusic(boolean on) {
        if (on)
            startService(musicService);
        else
            stopService(musicService);
    }


    // launch level selector activity
    public void onPlay(View view) {
        Intent intent = new Intent(this, LevelSelectorActivity.class);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    // launch leaderboard activity
    public void onLeaderboard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.putExtra("Username", username);
        startActivity(intent);
    }
}