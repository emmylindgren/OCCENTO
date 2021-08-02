package se.umu.emli.ou3.ui.home;

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

import java.util.Objects;

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
    private Button goToAddSongButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        goToAddSongButton = root.findViewById(R.id.add_song_button);

        //TODO: Fixa så den går till add song här också vid knapptryckning.
       // goToAddSongButton.setOnClickListener();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();;
    }
}