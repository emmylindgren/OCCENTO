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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.concurrent.TimeUnit;

import se.umu.emli.ou3.ui.gameResult.GameResultFragment;
import se.umu.emli.ou3.ui.gameResult.GameResultViewModel;
import se.umu.emli.ou3.ui.gameRound.GameRoundFragment;


/**
 * TODO: ViewModel class eller Viewclass?
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class GameActivity extends AppCompatActivity {


    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        addViewFlags();

          FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                 ft.replace(R.id.host_fragment_game, new GameRoundFragment());

                  ft.commit();



    }

    /**
     * Flags the activity UI so that fullscreen is on, kept on, and navbar is hidden
     * from user and is emerged when user slides down or out from where the bar usually is.
     */
    private void addViewFlags() {
        final int flags =  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.KEEP_SCREEN_ON
                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        getWindow().getDecorView().setSystemUiVisibility(flags);
    }

    public void goToResults(){

          FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                  ft.replace(R.id.host_fragment_game, new GameResultFragment());

                  ft.commit();


    }

}