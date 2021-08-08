package se.umu.emli.ou3.ui.gameResult;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import se.umu.emli.ou3.R;
import se.umu.emli.ou3.ui.gameRound.GameRoundFragment;

/**
 * View class.
 *
 * Handles UI and operating system interactions for showing the result of a gameround to the user.
 * That includes the total points of the round and the total number of songs shown in round.
 * User is also given two choices by buttons , to play another round or to go back to menu.
 * The choice is handled and user is sent to chosen destination.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class GameResultFragment extends Fragment {

    private GameResultViewModel gameResultViewModel;
    private View root;

    private TextView totalPoints;
    private TextView totalRounds;
    private Button goBackToMenuButton;
    private Button playAgainButton;

    public static GameResultFragment newInstance() {
        return new GameResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        gameResultViewModel = new ViewModelProvider(this).get(GameResultViewModel.class);
        root =inflater.inflate(R.layout.fragment_game_result, container, false);

        setUpViewItems();
        showResults();
        setUpListeners();

        return root;
    }

    /**
     * Shows the results for the user. Results are sent as a bundle to Gameresultfragment, bundle
     * is unpacked and textviews are updated accordingly.
     */
    private void showResults() {
        Bundle extras = getArguments();

        totalPoints.setText(Integer.toString(extras.getInt("totalPoints")));
        totalRounds.setText(Integer.toString(extras.getInt("totalNrOfRounds")));
    }

    /**
     * Starts another round of the game, by starting a new GameRoundFragment.
     */
    private void playAgain() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.host_fragment_game, new GameRoundFragment());
        ft.commit();
    }

    /**
     * Gets the user back to the "menu", by finishing the current Gameactivity and thus
     * getting back to the Mainactivity started first that's on the backstack.
     */
    private void goBackToMenu() {
        getActivity().finish();
    }

    /**
     * Sets up listeners for the view. In this case listeners for the two buttons
     * "play again" and "go back to menu".
     */
    private void setUpListeners() {
        goBackToMenuButton.setOnClickListener(v->goBackToMenu());
        playAgainButton.setOnClickListener(v->playAgain());
    }

    /**
     * Sets up the viewitems. Buttons and textviews to be listened on and changed.
     */
    private void setUpViewItems() {
        goBackToMenuButton =root.findViewById(R.id.go_to_menu_button);
        playAgainButton = root.findViewById(R.id.play_again_button);
        totalPoints= root.findViewById(R.id.nr_of_points_value);
        totalRounds = root.findViewById(R.id.total_nr_of_songs_value);
    }

}