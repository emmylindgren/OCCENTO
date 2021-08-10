package se.umu.emli.ou3.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import se.umu.emli.ou3.GameActivity;
import se.umu.emli.ou3.MainActivity;
import se.umu.emli.ou3.R;

/**
 * View class.
 *
 * Handles UI and operating system interactions for the user to start the game or go to add song
 * page for adding songs.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    private Button playGameButton;
    private Button goToAddSongButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);

        setUpViewItems();
        setUpListeners();

        return root;
    }

    /**
     * Sets up listeners for the two buttons "go to add song" and "play game".
     */
    private void setUpListeners() {
        goToAddSongButton.setOnClickListener(v -> goToAddSong());
        playGameButton.setOnClickListener(v->startGameRound());
    }

    /**
     * Sets up the viewitems for the view, the two buttons "go to add song" and "play game".
     */
    private void setUpViewItems() {
        goToAddSongButton = root.findViewById(R.id.add_song_button);
        playGameButton = root.findViewById(R.id.play_game_button);
    }

    /**
     * Navigates the user to add song fragment for adding songs.
     */
    private void goToAddSong(){
        ((MainActivity) requireActivity()).showAddSongFragment();
    }

    /**
     * Starts a gameround for the user. First checks if DB is empty, if it is then the user can't
     * play (there's no songs). Displays a snackbar telling the user to add songs to play the game.
     *
     * Otherwise start a new Game activity for user.
     */
    private void startGameRound(){
        if(homeViewModel.DBIsEmpty()){
            Snackbar.make(root, "Du måste ha låtar för att spela. Lägg till någon!",
                    Snackbar.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(getActivity(), GameActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}