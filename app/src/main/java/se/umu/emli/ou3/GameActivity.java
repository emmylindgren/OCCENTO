package se.umu.emli.ou3;


import android.os.Bundle;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

import se.umu.emli.ou3.ui.gameResult.GameResultFragment;
import se.umu.emli.ou3.ui.gameRound.GameRoundFragment;

/**
 * TODO: ViewModel class eller Viewclass?
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class GameActivity extends AppCompatActivity {

    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        addViewFlags();

        goToGameRound();
        setUpFragmentResultListeners();
    }

    private void setUpFragmentResultListeners() {
        manager.setFragmentResultListener("gameResults", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull @NotNull String requestKey, @NonNull @NotNull Bundle result) {
                int totalPoints = result.getInt("Points");
                int totalNrOfSongs = result.getInt("TotalSongs");

                goToResults(totalPoints,totalNrOfSongs);
            }
        });
    }

    private void goToGameRound() {
        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
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

    public void goToResults(int totalPoints,int totalNrOfRounds){

        Bundle gameResults = new Bundle();
        gameResults.putInt("totalPoints",totalPoints);
        gameResults.putInt("totalNrOfRounds",totalNrOfRounds);

        FragmentTransaction t = manager
                .beginTransaction();
        GameResultFragment gameResultFragment = new GameResultFragment();
        gameResultFragment.setArguments(gameResults);
        t.replace(R.id.host_fragment_game, gameResultFragment);
        t.commit();
    }
}