package se.umu.emli.ou3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsetsAnimationController;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

/**
 * TODO: ViewModel class eller Viewclass?
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView lyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        addViewFlags();
        setUpAccelerometerSensor();

        lyrics = findViewById(R.id.songLyric);



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
         * TODO: Ta bort denna, för testning.
         * lyrics.setText(Float.toString(x));
         *
         * TODO: Kalibrera denna nedan? Kanske måste pausa den också mellan gångerna?
         */

        if (x < -6) {
            lyrics.setText("Nedåt");
            //Då passar man. Sätt in en metod typ "passSong" eller så
        }
        else if (x > 6) {
            lyrics.setText("Uppåt");
            //Då får man poäng. Sätt in en metod typ räkna poäng och sen gå vidare? (samma som passa?)
        }
        else {
            lyrics.setText("ingen");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Unregisters the tilt listener when activity because listener is CPU and energy consuming.
     */
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}