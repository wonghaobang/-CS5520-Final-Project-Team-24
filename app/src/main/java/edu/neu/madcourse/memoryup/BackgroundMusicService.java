package edu.neu.madcourse.memoryup;

import androidx.annotation.Nullable;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


public class BackgroundMusicService extends Service {
    MediaPlayer musicPlayer;
    int currentSong = 0;
    static final int[] songs = new int[] {R.raw.the_cradle_of_your_soul, R.raw.cinematic_atmosphere,
            R.raw.price_of_freedom, R.raw.just_relax};

    @Override
    public void onCreate() {
        super.onCreate();

        // Music from https://pixabay.com/music/search/theme/background%20music/
        musicPlayer = MediaPlayer.create(this, songs[currentSong]);

        // set listener to move to next song
        musicPlayer.setOnCompletionListener(this::onCompletion);
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

    // move to next song when current one ends
    private void onCompletion(MediaPlayer mp) {
        // go to next song, or reset to first one
        if (currentSong + 1 < songs.length) {
            ++currentSong;
        } else {
            currentSong = 0;
        }

        // release finished song
        musicPlayer.release();

        // load and start next one
        musicPlayer = MediaPlayer.create(this, songs[currentSong]);
        musicPlayer.setOnCompletionListener(this::onCompletion);
        musicPlayer.start();
    }
}