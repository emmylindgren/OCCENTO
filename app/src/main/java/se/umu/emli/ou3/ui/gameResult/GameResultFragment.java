package se.umu.emli.ou3.ui.gameResult;

import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.umu.emli.ou3.R;

public class GameResultFragment extends Fragment {

    private GameResultViewModel gameResultViewModel;

    public static GameResultFragment newInstance() {
        return new GameResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        gameResultViewModel = new ViewModelProvider(this).get(GameResultViewModel.class);

        Bundle extras = getArguments();
        System.out.println(extras.getInt("totalPoints"));
        System.out.println(extras.getInt("totalNrOfRounds"));


        return inflater.inflate(R.layout.fragment_game_result, container, false);
    }


}