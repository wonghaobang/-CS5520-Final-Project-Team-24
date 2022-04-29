package edu.neu.madcourse.memoryup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.content.Context;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.neu.madcourse.memoryup.LeaderboardScreen.LeaderboardActivity;
import edu.neu.madcourse.memoryup.LevelSelectorScreen.LevelSelectorActivity;

public class MainActivity extends AppCompatActivity {
    // database
    private DatabaseReference reference;
    private String username = null;
    private UserData userData = null;

    // audio controls
    private boolean playMusic = false;
    private boolean playAudio = true;
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
            playAudio = savedInstanceState.getInt("playAudio") == 1;
        }

        // attempt to load username from local storage
        if (username == null)
            getUsername();

        SwitchCompat musicSwitch = findViewById(R.id.musicSwitch);
        musicSwitch.setOnCheckedChangeListener((compoundButton, b) -> toggleMusic(b));
        musicSwitch.setChecked(playMusic);

        SwitchCompat audioSwitch = findViewById(R.id.audioSwitch);
        audioSwitch.setOnCheckedChangeListener((compoundButton, b) -> toggleAudio(b));
        audioSwitch.setChecked(playAudio);
    }

    @Override
    protected void onDestroy() {
        // save username to file
        try {
            FileOutputStream fos = this.getBaseContext().openFileOutput("username", Context.MODE_PRIVATE);
            fos.write(username.getBytes());
            fos.close();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Fail to save username", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        super.onDestroy();
    }

    // don't prompt username on orientation changes
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (username != null) {
            outState.putString("username", username);
            outState.putInt("playMusic", (playMusic ? 0 : 1));
            outState.putInt("playMusic", (playAudio ? 0 : 1));
        }
        super.onSaveInstanceState(outState);
    }

    // load saved username, if it exists
    private void getUsername() {
        try {
            FileInputStream file = this.openFileInput("username");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            username = reader.readLine();
            file.close();
            reader.close();
        } catch (Exception e) {
            // no file was found
            promptLogin();
        }

        // just in case file was invalid
        if (username == null)
            promptLogin();
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
                        userData = new UserData(0, 0, 0);
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
        playMusic = on;

        if (on) {
            startService(musicService);
        }
        else {
            stopService(musicService);
        }
    }

    // start background music
    private void toggleAudio(boolean on) {
        playAudio = on;
    }

    // launch level selector activity
    public void onPlay(View view) {
        Intent intent = new Intent(this, LevelSelectorActivity.class);
        intent.putExtra("Username", username);
        intent.putExtra("Audio", playAudio);
        startActivity(intent);
    }

    // launch leaderboard activity
    public void onLeaderboard(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    // change username
    public void onChangeUser(View view) {
        promptLogin();
    }
}