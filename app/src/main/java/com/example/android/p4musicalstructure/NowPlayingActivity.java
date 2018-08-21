package com.example.android.p4musicalstructure;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class NowPlayingActivity extends AppCompatActivity {

    private int listPosition;
    private boolean isPlayIcon = false;
    private int prevClickCount = 0;
    private ArrayList<Song> songs;
    private SeekBar mSeekBar;
    private TextView mCurrentSongName;
    private TextView mCurrentSongArtist;
    private TextView mCurrentSongProgress;
    private TextView mCurrentSongMaxDuration;
    private ImageButton mPlayPauseButton;
    private Handler mHandler;
    private Runnable mRunnable;
    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mMediaPlayer;
    /**
     * Handles audio focus when playing a sound file
     */
    private AudioManager mAudioManager;
    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a short amount of time.
                // The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that our app is allowed to continue playing sound but at a lower volume.
                // We'll treat both cases the same way because our app is playing short sound files. Pause playback and reset player to the
                // start of the file. That way, we can play the word from the beginning when we resume playback because it's better this way in this app.
                mMediaPlayer.pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback from where we stopped.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // if it's not the last song in the list, move to the next song
            if (listPosition < (songs.size() - 1)) {
                listPosition += 1;
                currentSong();
                // if the play-pause button is set to play mode (the pause image is displayed), play the next song
                if (isPlayIcon == false)
                    mMediaPlayer.start();
                // if it's the last song, set the play-pause button to pause mode (the play image is displayed), and "reset" the song progress
            } else if (listPosition == (songs.size() - 1)) {
                mPlayPauseButton.setImageResource(R.drawable.ic_play_circle_filled);
                isPlayIcon = true;
                mMediaPlayer.seekTo(0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing);
        // Helps to set the seekbar
        mHandler = new Handler();
        // Add up-button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Find views
        TextView mCurrentSongGenre = (TextView) findViewById(R.id.song_genre);
        ImageView mCurrentGenreImage = (ImageView) findViewById(R.id.current_genre_image);
        mCurrentSongName = (TextView) findViewById(R.id.current_song_name);
        mCurrentSongArtist = (TextView) findViewById(R.id.current_song_artist);
        mCurrentSongProgress = (TextView) findViewById(R.id.song_progress);
        mCurrentSongMaxDuration = (TextView) findViewById(R.id.song_max_duration);
        ImageButton mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPlayPauseButton = (ImageButton) findViewById(R.id.play_pause_button);
        ImageButton mNextButton = (ImageButton) findViewById(R.id.next_button);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // Get the list of songs from the activity that sent the intent
        songs = getIntent().getParcelableArrayListExtra("list");
        final int DEFAULT_POSITION = 0;
        // Get the chosen song position in the list
        listPosition = getIntent().getIntExtra("position", DEFAULT_POSITION);
        // Get the chosen song genre
        mCurrentSongGenre.setText(getIntent().getStringExtra("genre"));
        // Get the song genre image
        mCurrentGenreImage.setImageResource(getIntent().getIntExtra("genreImage", R.drawable.ic_musicplayer));
        // Display chosen song details, and request for audio focus
        currentSong();
        // Start the audio file
        mMediaPlayer.start();
        // The code in this method will be executed when the "Next" ImageButton is clicked on.
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if the current song is not the last song on the list, move to the next song. if in addition we're in
                // "play" mode, it will start playing.
                if (listPosition < (songs.size() - 1)) {
                    listPosition += 1;
                    currentSong();
                    if (isPlayIcon == false)
                        mMediaPlayer.start();
                }
            }
        });
        // The code in this method will be executed when the "Previous" ImageButton is clicked on.
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevClickCount += 1;
                // if the "previous" button is clicked for the first time in the current song, or the current song is the
                // first song on the list, reset the song position in time. if in addition we're in "play" mode, it will start
                // playing from the beginning.
                if (prevClickCount == 1 || listPosition == 0) {
                    if(mMediaPlayer.getCurrentPosition()!=0) {
                        mMediaPlayer.seekTo(0);
                    }
                    else{
                        if(listPosition!=0) {
                            prevClickCount = 0;
                            listPosition -= 1;
                            currentSong();
                        }
                    }
                    if (isPlayIcon == false)
                        mMediaPlayer.start();
                    // if the "previous" button is clicked for the second time in a row, in the current song, or the current song is
                    // not the first song on the list, move to the previous song. if in addition we're in "play" mode, it will start playing.
                } else if (prevClickCount == 2 && listPosition > 0) {
                    prevClickCount = 0;
                    listPosition -= 1;
                    currentSong();
                    if (isPlayIcon == false)
                        mMediaPlayer.start();
                }
            }
        });
        // The code in this method will be executed when the "Play"/"Pause" ImageButton is clicked on.
        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if the "Pause" button is clicked, pause the music and change the button to "Play"
                if (isPlayIcon == false) {
                    mMediaPlayer.pause();
                    mPlayPauseButton.setImageResource(R.drawable.ic_play_circle_filled);
                    isPlayIcon = true;
                    // if the "Play" button is clicked, play the music and change the button to "Pause"
                } else if (isPlayIcon == true) {
                    mMediaPlayer.start();
                    mPlayPauseButton.setImageResource(R.drawable.ic_pause_circle_filled);
                    isPlayIcon = false;
                }
            }
        });
        // The code in this method will be executed whenever the user touches the seekbar
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // update the song position (in time progress) to the new place of the thumb
                    mMediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    /**
     * Create and display the song duration and current progress (in seconds)
     */
    protected void getAudioStatus() {
        // Time format MM:SS for the current song duration
        long durationMM = TimeUnit.MILLISECONDS.toMinutes(mMediaPlayer.getDuration());
        long durationSS = TimeUnit.MILLISECONDS.toSeconds(mMediaPlayer.getDuration()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mMediaPlayer.getDuration()));
        String durationFormatMM = "%d:";
        String durationFormatSS = "%d";
        if (durationMM < 10)
            durationFormatMM = "0%d:";
        if (durationSS < 10)
            durationFormatSS = "0%d";
        // Time format MM:SS for the current song current time position
        long passMM = TimeUnit.MILLISECONDS.toMinutes(mMediaPlayer.getCurrentPosition());
        long passSS = TimeUnit.MILLISECONDS.toSeconds(mMediaPlayer.getCurrentPosition()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mMediaPlayer.getCurrentPosition()));
        String passFormatMM = "%d:";
        String passFormatSS = "%d";
        if (passMM < 10)
            passFormatMM = "0%d:";
        if (passSS < 10)
            passFormatSS = "0%d";
        // Display the time formats
        mCurrentSongMaxDuration.setText(String.format(durationFormatMM + durationFormatSS, durationMM, durationSS));
        mCurrentSongProgress.setText(String.format(passFormatMM + passFormatSS, passMM, passSS));
    }

    /**
     *  Get details about the song time parameters, and show the song progress on the seekbar
     */
    protected void initSeekBar() {
        mSeekBar.setMax(mMediaPlayer.getDuration() / 1000);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mMediaPlayer != null) {
                    int mCurrentPosition = mMediaPlayer.getCurrentPosition() / 1000;
                    mSeekBar.setProgress(mCurrentPosition);
                    getAudioStatus();
                }
                mHandler.postDelayed(mRunnable, 1000);
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }

    /**
     * Display the current song details (name and artist), and request for audio focus
     */
    public void currentSong() {
        prevClickCount = 0;
        // Release the media player if it currently exists because we are about to play a different sound file.
        releaseMediaPlayer();
        Song currentSong = songs.get(listPosition);
        mCurrentSongName.setText(currentSong.getSongName());
        mCurrentSongArtist.setText(currentSong.getArtistName());
        // Request audio focus in order to play the audio file. The app needs to play a long audio file (song),
        // so we will request audio focus with an unknown duration with AUDIOFOCUS_GAIN.
        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // We have audio focus now
            mMediaPlayer = MediaPlayer.create(NowPlayingActivity.this, currentSong.getAudioResourceId());
            initSeekBar();
            // Setup a listener on the media player, so that we can stop and release the media player once the sound has finished playing.
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources because we no longer need it.
            mMediaPlayer.release();
            // Set the media player back to null. For our code, we've decided the setting the media player to null is an easy way to tell
            // that the media player is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            // Regardless of whether or not we were granted audio focus, abandon it. This also unregisters the AudioFocusChangeListener
            // so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    /**
     * Implementation for the Up-button functionality of "Now Playing" screen (solve the multiple parents "problem")
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (getIntent().getStringExtra("genre").equals("Forró")) {
                releaseMediaPlayer();
                Intent intent = new Intent(NowPlayingActivity.this, ForroActivity.class);
                startActivity(intent);
                return true;
            } else if (getIntent().getStringExtra("genre").equals("Capoeira")) {
                releaseMediaPlayer();
                Intent intent = new Intent(NowPlayingActivity.this, CapoeiraActivity.class);
                startActivity(intent);
                return true;
            } else if (getIntent().getStringExtra("genre").equals("Sertanejo")) {
                releaseMediaPlayer();
                Intent intent = new Intent(NowPlayingActivity.this, SertanejoActivity.class);
                startActivity(intent);
                return true;
            } else if (getIntent().getStringExtra("genre").equals("Samba de Gafieira")) {
                releaseMediaPlayer();
                Intent intent = new Intent(NowPlayingActivity.this, SambaDeGafieiraActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
