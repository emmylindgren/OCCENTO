package se.umu.emli.ou3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.concurrent.TimeUnit;


/**
 * TODO: ViewModel class eller Viewclass?
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView textViewtimer;
    private TextView songLyrics;
    private TextView songTitle;
    private TextView songArtist;

    private CountDownTimer countDownTimer;
  //  private SongRepository repository;

    private RandomSongGenerator randomSongGenerator;
  //  private Queue<Song> randomSongs;
    private static final String TIME_FORMAT = "%02d:%02d";

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        addViewFlags();
        setUpAccelerometerSensor();

        randomSongGenerator = new RandomSongGenerator(getApplication());
       // repository = new SongRepository(getApplication());

        setUpViewItems();

        startCountDown();
        setNextRandomSong();

    }


    private void setNextRandomSong() {
        /**
         *

        if(randomSongs == null || randomSongs.isEmpty()){
            randomSongs = repository.getRandomSongs();
        }
         */

        setCurrentSongView(randomSongGenerator.getRandomSong());
    }

    private void setCurrentSongView(Song song) {
        songArtist.setText(song.getArtist());
        songTitle.setText(song.getTitle());
        songLyrics.setText(song.getLyrics());
    }

    private void setUpViewItems() {
        textViewtimer = findViewById(R.id.timer);
        songLyrics = findViewById(R.id.songLyric);
        songTitle = findViewById(R.id.songTitle);
        songArtist = findViewById(R.id.songArtist);
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
                //avsluta spelet.

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
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * Adding flags to View keeping the view on fullscreen and awake for game session.
     */
    private void addViewFlags() {
        final int flags =  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.KEEP_SCREEN_ON
                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        getWindow().getDecorView().setSystemUiVisibility(flags);
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

        /**
         *
         * TODO: Kalibrera denna nedan? Kanske måste pausa den också mellan gångerna?
         */

        if (x < -8) {
            songLyrics.setText("Fel!");
            sensorManager.unregisterListener(this);
            handler.postDelayed(new Runnable() {
                public void run() {
                    setUpNewSong();
                }
            }, 2000);
            // här vill vi visa "fel"
            //Räkna inte poäng men eventuellt räkna hur många låtar körda?
        }
        else if (x > 8) {
            songLyrics.setText("Uppåt");
            //setCurrentSongView(randomSongGenerator.getRandomSong());
            //Vill visa typ "rätt!" här eller nåt. PLUS måste pausa så man inte råkar dubbla
            //Då får man poäng. Sätt in en metod typ räkna poäng
            View view = findViewById(R.id.songLyric);
            View root = view.getRootView();
            root.setBackgroundColor(ContextCompat.getColor(this,R.color.secondary_blue));
        }
        else {
            //Här har vi ingen lutning
        }

    }

    private void setUpNewSong() {
        setCurrentSongView(randomSongGenerator.getRandomSong());
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Unregisters the tilt listener when activity because listener is CPU and energy consuming.
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }
}