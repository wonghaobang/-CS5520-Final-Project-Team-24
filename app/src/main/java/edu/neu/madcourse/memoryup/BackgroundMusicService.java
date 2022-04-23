package edu.neu.madcourse.memoryup;

import androidx.annotation.Nullable;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;


public class BackgroundMusicService extends Service {
    MediaPlayer musicPlayer;
    int currentSong = 0;
    static final int[] songs = new int[] {R.raw.the_cradle_of_your_soul};

    @Override
    public void onCreate() {
        super.onCreate();

        // Music from https://pixabay.com/music/search/theme/background%20music/
        musicPlayer = MediaPlayer.create(this, songs[currentSong]);
        musicPlayer.setVolume(100, 100);

        // loop through songs when current finishes
        musicPlayer.setOnCompletionListener(mp -> {
            // go to next song, or reset to first one
            if (currentSong + 1 < songs.length) {
                ++currentSong;
            }
            else {
                currentSong = 0;
            }

            // reset player
            musicPlayer.reset();

            // load next song
            ++currentSong;
            String nextSong = "android:resource://" + this.getPackageName() + "/raw/" + songs[currentSong];
            try {
                mp.setDataSource(this, Uri.parse(nextSong));
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                Toast.makeText(this, "Failed to load song", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
        musicPlayer.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicPlayer.release();
    }
}