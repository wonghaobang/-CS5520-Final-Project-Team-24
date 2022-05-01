package edu.neu.madcourse.memoryup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    // notifications
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect to firebase and set user data
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        musicService = new Intent(this, BackgroundMusicService.class);

        if (savedInstanceState != null) {
           playMusic = savedInstanceState.getInt("playMusic") == 1;
            playAudio = savedInstanceState.getInt("playAudio") == 1;
        }

        // attempt to load username from local storage
        getUsername();

        // get/update user country if permissions are enabled
        getCountry();

        SwitchCompat musicSwitch = findViewById(R.id.musicSwitch);
        musicSwitch.setOnCheckedChangeListener((compoundButton, b) -> toggleMusic(b));
        musicSwitch.setChecked(playMusic);

        SwitchCompat audioSwitch = findViewById(R.id.audioSwitch);
        audioSwitch.setOnCheckedChangeListener((compoundButton, b) -> toggleAudio(b));
        audioSwitch.setChecked(playAudio);

        // set up notifications
        createNotificationChannel();
        launchNotificationListener();
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
        outState.putInt("playMusic", (playMusic ? 0 : 1));
        outState.putInt("playMusic", (playAudio ? 0 : 1));
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

            // display it
            showUsername();
        } catch (Exception e) {
            // no file was found
            AlertDialog dialog = promptLogin();
            dialog.show();
        }

        // just in case file was invalid
        if (username == null) {
            AlertDialog dialog = promptLogin();
            dialog.show();
        }
    }

    // get the country of the user's location, if permissions allow
    private void getCountry () {
        LocationManager sensor = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // set provider based on permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.getCurrentLocation(sensor);
        } else {
            final String message = "Enable permissions to share your country on the leaderboard.";
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.show();

            ActivityResultLauncher<String[]> permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result ->
            {
                // request permission for both coarse and fine, but accepting just coarse is sufficient
                Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false);

                if ((fineLocationGranted != null && fineLocationGranted) || (coarseLocationGranted != null && coarseLocationGranted)) {
                    this.getCurrentLocation(sensor);
                }
            });

            permissionLauncher.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }

    // get provider type based on permissions and availability
    @SuppressLint("MissingPermission")  // always called after permissions are checked
    private void getCurrentLocation(LocationManager sensor) {
        List<String> providers = sensor.getProviders(true);

        // get location
        Location bestLocation = null;
        for (String provider : providers) {
            Location location = sensor.getLastKnownLocation(provider);
            if (location == null)
                continue;

            if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = location;
            }
        }

        if (bestLocation == null) {
            return;
        }

        // launch geocoder to get country name
        String country = "";
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(bestLocation.getLatitude(), bestLocation.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                country = addresses.get(0).getCountryName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if country has changed, send to database
        if (!country.equals("")) {
            reference.child(username).setValue(userData);
        }
    }

    // request username entry
    private AlertDialog promptLogin() {
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

        return dialog;
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
                        saveUsername();
                        showUsername();
                    } else {
                        Snackbar.make(view, R.string.signup_failure, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (logIn) {
                        Snackbar.make(view, R.string.login_failure, Snackbar.LENGTH_SHORT).show();
                    } else {
                        userData = new UserData("", 0, 0, 0);
                        reference.child(etUsername).setValue(userData);

                        username = etUsername;
                        dialog.dismiss();
                        saveUsername();
                        showUsername();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to read data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // create notification channel
    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
        channel.setDescription(description);

        // Register the channel with the system
        notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    // launch listener for notifications to send
    private void launchNotificationListener() {
        // add a listener for changes to given user
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot changedUser : snapshot.getChildren()) {
                    UserData changedData = changedUser.getValue(UserData.class);
                    String changedUsername = changedUser.getKey();

                    if (changedUsername == null || changedData == null)
                        continue;

                    // for current user, just update stored data
                    if (changedUsername.equals(username)) {
                        userData = changedData;
                        continue;
                    }

                    // send notification when other user exceed your score
                    int userScore = userData.maxAnimal + userData.maxFruit + userData.maxPlanet;
                    int changedScore = changedData.maxAnimal + changedData.maxFruit + changedData.maxPlanet;
                    if (changedScore > userScore) {
                        sendNotification(changedUsername, changedData.country);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
        reference.addValueEventListener(postListener);
    }

    // send a notification
    private void sendNotification(String otherUser, String otherCountry) {
        // check with saved data to see when last notification was sent
        long currentTime = new Date().getTime() / 1000;
        try {
            FileInputStream file = this.openFileInput("lastNotification");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            int lastTime = Integer.parseInt(reader.readLine());
            file.close();
            reader.close();

            // if last notification was under a day ago, do nothing
            if (currentTime - lastTime < 86400)
                return;
        } catch (Exception ignored) { } // no file was found, so sending is okay

        String message;
        if (!otherCountry.equals(""))
            message = otherUser + " just passed you on the leaderboard. Reclaim your spot!";
        else
            message = otherUser + "from " + otherCountry + " just passed you on the leaderboard. Reclaim your spot!";

       NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.icon_background)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(0, builder.build());

        // save sent time to file
        try {
            FileOutputStream fos = this.getBaseContext().openFileOutput("lastNotification", Context.MODE_PRIVATE);
            fos.write(String.valueOf(currentTime).getBytes());
            fos.close();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Notification time not saved", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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
        AlertDialog dialog = promptLogin();
        dialog.setCancelable(true);
        dialog.show();
    }

    // save username to file
    private void saveUsername() {
       try {
            FileOutputStream fos = this.getBaseContext().openFileOutput("username", Context.MODE_PRIVATE);
            fos.write(username.getBytes());
            fos.close();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Username not saved", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // show username on screen
    private void showUsername() {
        // display
        TextView userGreeting = findViewById(R.id.userGreeting);
        userGreeting.setText(getString(R.string.user_greeting, username));
    }
}