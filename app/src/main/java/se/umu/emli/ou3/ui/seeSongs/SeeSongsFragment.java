package se.umu.emli.ou3.ui.seeSongs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import se.umu.emli.ou3.R;
import se.umu.emli.ou3.SongAdapter;

/**
 * View class.
 *
 * Handles UI and operating system interactions for the user to be displaying all songs in the db.
 * The user can swipe a song to the right to delete it from the db, in which case a
 * snackbar is showed to inform the user that the song has been deleted.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class SeeSongsFragment extends Fragment {

    private SeeSongsViewModel seeSongsViewModel;
    private RecyclerView songRecyclerView;
    private View root;
    private SongAdapter songAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        seeSongsViewModel =
                new ViewModelProvider(this).get(SeeSongsViewModel.class);

        root = inflater.inflate(R.layout.fragment_see_songs, container, false);

        setUpRecycler();
        seeSongsViewModel.getAllSongs().observe(requireActivity(), songs -> songAdapter.setSongs(songs));
        startItemTouchHelper();

        return root;
    }

    private void startItemTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView,
                                  @NonNull @NotNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder,
                                 int direction){
                seeSongsViewModel.delete(songAdapter.getSongAt(viewHolder.getAdapterPosition()));
                Snackbar.make(root,"Låt raderad", Snackbar.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(songRecyclerView);
    }

    private void setUpRecycler() {
        songRecyclerView = root.findViewById(R.id.song_recycler_view);

        songRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        songRecyclerView.setHasFixedSize(true);

        songAdapter = new SongAdapter();
        songRecyclerView.setAdapter(songAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}