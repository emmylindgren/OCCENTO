package se.umu.emli.ou3.ui.addSong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import se.umu.emli.ou3.R;
import se.umu.emli.ou3.Song;

public class AddSongFragment extends Fragment {

    private AddSongViewModel addSongViewModel;
    private EditText editTextTitle;
    private EditText editTextArtist;
    private EditText editTextLyrics;
    private Button saveSongButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addSongViewModel =
                new ViewModelProvider(this).get(AddSongViewModel.class);

        View root = inflater.inflate(R.layout.fragment_add_song, container, false);

        editTextTitle = root.findViewById(R.id.title_edit_text);
        editTextArtist = root.findViewById(R.id.artist_edit_text);
        editTextLyrics = root.findViewById(R.id.lyrics_edit_text);
        saveSongButton = root.findViewById(R.id.save_song);

        saveSongButton.setOnClickListener(this::addSong);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void addSong(View view){
        String title = editTextTitle.getText().toString();
        String artist = editTextArtist.getText().toString();
        String lyrics = editTextLyrics.getText().toString();

        //Trim is used to avoid accepting input from user in form of blank spaces.
        if(title.trim().isEmpty() || artist.trim().isEmpty() || lyrics.trim().isEmpty()){
            Snackbar.make(view,R.string.incorrect_song_add, Snackbar.LENGTH_SHORT).show();
            hideKeyboard(view);
            return;
        }

        addSongViewModel.insert(new Song(title,artist,lyrics,true));
        Snackbar.make(view,R.string.song_added, Snackbar.LENGTH_SHORT).show();
        editTextTitle.getText().clear();
        editTextArtist.getText().clear();
        editTextLyrics.getText().clear();

        hideKeyboard(view);
    }

    /**
     * Hides the keyboard.
     * @param view
     */
    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(requireActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
