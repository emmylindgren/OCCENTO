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

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import se.umu.emli.ou3.R;
import se.umu.emli.ou3.RandomSongGenerator;
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

    private CountDownTimer countDownTimer;

    private RandomSongGenerator randomSongGenerator;
    private static final String TIME_FORMAT = "%02d:%02d";
    boolean positionIsReset;

    private Handler handler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        gameRoundViewmodel =
                new ViewModelProvider(this).get(GameRoundViewModel.class);

        root = inflater.inflate(R.layout.fragment_game_round, container, false);

        addViewFlags();
        setUpAccelerometerSensor();

        randomSongGenerator = new RandomSongGenerator(getActivity().getApplication());

        setUpViewItems();

        startCountDown();
        setCurrentSongView(randomSongGenerator.getRandomSong());

        return root;
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

    private void startCountDown() {
        countDownTimer = new CountDownTimer(180200, 1000) {

            public void onTick(long millisUntilFinished) {
                textViewtimer.setText("" + String.format(TIME_FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                textViewtimer.setText("done!");
                //avsluta spelet, aka byt till ett annat fragment; visa poäng fragment.
            }
        }.start();
    }

    void cancelTimer() {
        if(countDownTimer!=null)
            countDownTimer.cancel();
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
     * Adding flags to View keeping the view on fullscreen and awake for game session.
     * TODO: dessa flaggor kanske kan va i aktiviteten?
     */
    private void addViewFlags() {
        final int flags =  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.KEEP_SCREEN_ON
                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        getActivity().getWindow().getDecorView().setSystemUiVisibility(flags);
    }

    /**
     * When the device moves in any direction the method is triggered.
     * Method is set to take action when device is held horizontal (against forehead) and
     * is tilted up or down, to collect points or pass on the current song.
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[2];

        if(positionIsReset){
            if (x < -7) {
                positionIsReset = false;
                updateUI(R.raw.pass, "Pass!", R.color.red_for_pass);
                //TODO: Räkna inte poäng men eventuellt räkna hur många låtar körda?
                nextRound();

            }
            else if (x > 7) {
                positionIsReset = false;
                updateUI(R.raw.point, "Poäng!", R.color.green_for_points);
                // TODO: Då får man poäng. Sätt in en metod typ räkna poäng
                nextRound();
            }
        }
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
        setCurrentSongView(randomSongGenerator.getRandomSong());
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
        //TODO: Avregistrera lyssnare också? det kanske görs automatiskt?
        cancelTimer();
    }
}