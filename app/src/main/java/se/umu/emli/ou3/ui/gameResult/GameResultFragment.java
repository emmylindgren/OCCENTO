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

    private void showResults() {
        Bundle extras = getArguments();

        totalPoints.setText(Integer.toString(extras.getInt("totalPoints")));
        totalRounds.setText(Integer.toString(extras.getInt("totalNrOfRounds")));
    }

    private void playAgain() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.host_fragment_game, new GameRoundFragment());
        ft.commit();
    }

    private void goBackToMenu() {
        getActivity().finish();
    }

    private void setUpListeners() {
        goBackToMenuButton.setOnClickListener(v->goBackToMenu());
        playAgainButton.setOnClickListener(v->playAgain());
    }

    private void setUpViewItems() {
        goBackToMenuButton =root.findViewById(R.id.go_to_menu_button);
        playAgainButton = root.findViewById(R.id.play_again_button);
        totalPoints= root.findViewById(R.id.nr_of_points_value);
        totalRounds = root.findViewById(R.id.total_nr_of_songs_value);
    }

}