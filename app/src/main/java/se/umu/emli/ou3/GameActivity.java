package se.umu.emli.ou3;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import se.umu.emli.ou3.ui.gameResult.GameResultFragment;
import se.umu.emli.ou3.ui.gameRound.GameRoundFragment;

/**
 *
 * Gameactivity to manage gamefragments like playing a round of the game and
 * then seeing the results of that round. Keeping the screen on and the navbars hidden
 * for this activity also.
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

    /**
     * Sets up fragmentresultslisteners to listen for when fragment Gameround returns a
     * result, aka the points and how many songs were played in that round. When result is returned
     * a GameResult fragment is started with arguments total points and total number of songs
     * played in the round.
     */
    private void setUpFragmentResultListeners() {
        manager.setFragmentResultListener("gameResults", this,
                (requestKey, result) -> {
            int totalPoints = result.getInt("Points");
            int totalNrOfSongs = result.getInt("TotalSongs");

            goToResults(totalPoints,totalNrOfSongs);
        });
    }

    /**
     * Starts a new Gameround fragment, aka a new round of the game.
     */
    private void goToGameRound() {
        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.host_fragment_game, new GameRoundFragment());
        ft.commit();
    }

    /**
     * Flags the activity UI so that fullscreen is on, kept on, and navbar is hidden
     * from user and is emerged when user slides down or out from where the bar usually is. This is
     * so that the game gets more focus and user does not accidentally press any button as the phone
     * is on the users forehead.
     */
    private void addViewFlags() {
        final int flags =  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.KEEP_SCREEN_ON
                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        getWindow().getDecorView().setSystemUiVisibility(flags);
    }

    /**
     * Starts a new GameResults fragment, with total points this round and total songs
     * this round sent in to be displayed in that fragment.
     * @param totalPoints, the total points of the round.
     * @param totalNrOfRounds, the total nr of songs this round.
     */
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