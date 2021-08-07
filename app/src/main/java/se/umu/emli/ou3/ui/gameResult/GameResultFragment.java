package se.umu.emli.ou3.ui.gameResult;

import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import se.umu.emli.ou3.GameActivity;
import se.umu.emli.ou3.MainActivity;
import se.umu.emli.ou3.R;

public class GameResultFragment extends Fragment {

    private GameResultViewModel gameResultViewModel;
    private View root;

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
        System.out.println(extras.getInt("totalPoints"));
        System.out.println(extras.getInt("totalNrOfRounds"));
    }

    private void playAgain() {
    }

    private void goBackToMenu() {
        /*Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);*/
        getActivity().finish();
    }

    private void setUpListeners() {
        goBackToMenuButton.setOnClickListener(v->goBackToMenu());
        playAgainButton.setOnClickListener(v->playAgain());
    }

    private void setUpViewItems() {
        goBackToMenuButton =root.findViewById(R.id.go_to_menu_button);
        playAgainButton = root.findViewById(R.id.play_again_button);
    }

}