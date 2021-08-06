package se.umu.emli.ou3.ui.gameRound;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import se.umu.emli.ou3.GameActivity;
import se.umu.emli.ou3.MainActivity;
import se.umu.emli.ou3.R;
import se.umu.emli.ou3.Song;

public class GameRoundFragment extends Fragment implements SensorEventListener {

    private GameRoundViewModel gameRoundViewmodel;
    private View root;

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView textViewtimer;
    private TextView songLyrics;
    private TextView songTitle;
    private TextView songArtist;

    private static final String TIME_FORMAT = "%02d:%02d";
    boolean positionIsReset;

    private Handler handler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        gameRoundViewmodel =
                new ViewModelProvider(this).get(GameRoundViewModel.class);

        root = inflater.inflate(R.layout.fragment_game_round, container, false);

        setUpAccelerometerSensor();
        setUpViewItems();

        startTimer();
        setCurrentSongView(gameRoundViewmodel.getNextRandomSong());

        return root;
    }

    private void startTimer() {
        gameRoundViewmodel.startCountDown();
        gameRoundViewmodel.getTimerLiveData().observe(requireActivity(),this::updateTimer);
    }

    private void setCurrentSongView(Song song) {
        songArtist.setText(song.getArtist());
        songTitle.setText(song.getTitle());
        songLyrics.setText(song.getLyrics());
    }

    private void setUpViewItems() {
        textViewtimer = root.findViewById(R.id.timer);
        songLyrics = root.findViewById(R.id.songLyric);
        songTitle = root.findViewById(R.id.songTitle);
        songArtist = root.findViewById(R.id.songArtist);
    }

    /**
     * Updates the UI timer to show user how much time is left on their turn. When
     * timer hits 0 seconds left, the game round is ended.
     * @param timeLeft
     */
    private void updateTimer(Long timeLeft){
        textViewtimer.setText(String.format(Locale.GERMAN,TIME_FORMAT,
                TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                        TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
        if(timeLeft == 0){
            endRound();
        }
    }

    private void endRound() {
        //TODO: Ta bort detta sen.
        //((GameActivity) requireActivity()).goToResults();
        Bundle result = new Bundle();
        result.putInt("Points",gameRoundViewmodel.getPointsThisRound());
        result.putInt("TotalSongs",gameRoundViewmodel.getTotalOfSongsThisRound());
        getParentFragmentManager().setFragmentResult("gameResults", result);

    }

    /**
     * Setting up the accelerometer motion sensor for monitoring the devices motion.
     */
    private void setUpAccelerometerSensor() {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        positionIsReset = true;
    }

    /**
     * When the device moves in any direction the method is triggered.
     * Method is set to take action when device is held horizontal (against forehead) and
     * is tilted up or down, to collect points or pass on the current song.
     *
     * A boolean is used to make sure points are not collected or songs are not passed several
     * times for one movement.
     * @param event, the event that is listened for.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[2];

        if(positionIsReset){
            // User tilts the phone downwards and passes the song, a new song is displayed.
            if (x < -7) {
                positionIsReset = false;
                updateUI(R.raw.pass, "Pass!", R.color.red_for_pass);
                gameRoundViewmodel.noPoint();
                nextRound();

            }
            //User tilts the phone upwards, gets a point and a new song is displayed.
            else if (x > 7) {
                positionIsReset = false;
                updateUI(R.raw.point, "Poäng!", R.color.green_for_points);
                gameRoundViewmodel.addPoint();
                nextRound();
            }
        }
        // The boolean is reset when user holds the phone againts forehead again.
        else if( -7< x && x < 7) {
            positionIsReset = true;
        }
    }

    private void updateUI(int p, String s, int p2) {
        sensorManager.unregisterListener(this);
        playSoundEffect(p);
        blankOutOldSong(s);
        setBackground(p2);
    }

    private void setBackground(int p) {
        root.setBackgroundColor(ResourcesCompat.getColor(getResources(), p, null));
    }

    private void playSoundEffect(int p) {
        MediaPlayer.create(requireContext(), p).start();
    }

    private void blankOutOldSong(String passOrPoint) {
        songLyrics.setText(passOrPoint);
        songTitle.setText("");
        songArtist.setText("");
    }

    private void nextRound() {
        handler.postDelayed(this::setUpNewSongRound, 1000);
    }

    private void setUpNewSongRound() {
        setCurrentSongView(gameRoundViewmodel.getNextRandomSong());
        setBackground(R.color.primary_green);
        sensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Unregisters the tilt listener when activity because listener is CPU and energy consuming.
     */
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gameRoundViewmodel.cancelTimer();
    }
}