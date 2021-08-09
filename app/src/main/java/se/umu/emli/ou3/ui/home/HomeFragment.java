package se.umu.emli.ou3.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import se.umu.emli.ou3.GameActivity;
import se.umu.emli.ou3.MainActivity;
import se.umu.emli.ou3.R;
import se.umu.emli.ou3.SongRepository;

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
        goToAddSongButton.setOnClickListener(v -> goToAddSong());
        playGameButton.setOnClickListener(v->startGameRound());

        return root;
    }

    private void setUpViewItems() {
        goToAddSongButton = root.findViewById(R.id.add_song_button);
        playGameButton = root.findViewById(R.id.play_game_button);
    }

    private void goToAddSong(){
        ((MainActivity) requireActivity()).showAddSongFragment();
    }

    private void startGameRound(){
        SongRepository repository = new SongRepository(getActivity().getApplication());
        if(repository.isSongDBEmpty()){
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
        super.onDestroyView();;
    }
}