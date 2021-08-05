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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.concurrent.TimeUnit;

import se.umu.emli.ou3.ui.gameRound.GameRoundFragment;


/**
 * TODO: ViewModel class eller Viewclass?
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class GameActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.host_fragment_game, new GameRoundFragment());

        ft.commit();

    }

}