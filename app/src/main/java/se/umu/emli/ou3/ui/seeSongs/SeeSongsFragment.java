package se.umu.emli.ou3.ui.seeSongs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import se.umu.emli.ou3.R;
import se.umu.emli.ou3.SongAdapter;

public class SeeSongsFragment extends Fragment {

    private SeeSongsViewModel seeSongsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        seeSongsViewModel =
                new ViewModelProvider(this).get(SeeSongsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_see_songs, container, false);

        RecyclerView songRecyclerView = root.findViewById(R.id.song_recycler_view);
        songRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        songRecyclerView.setHasFixedSize(true);

        SongAdapter songAdapter = new SongAdapter();
        songRecyclerView.setAdapter(songAdapter);


        seeSongsViewModel.getAllSongs().observe(requireActivity(), songs -> songAdapter.setSongs(songs));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}