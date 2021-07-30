package se.umu.emli.ou3.ui.addSong;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import se.umu.emli.ou3.R;
import se.umu.emli.ou3.Song;

public class AddSongFragment extends Fragment {

    private AddSongViewModel addSongViewModel;
    private EditText editTextTitle;
    private EditText editTextArtist;
    private EditText editTextLyrics;
    private TextView nrOfCharsLyrics;
    private Button saveSongButton;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addSongViewModel =
                new ViewModelProvider(this).get(AddSongViewModel.class);

        root = inflater.inflate(R.layout.fragment_add_song, container, false);

        setUpViewItems();
        setUpListeners();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause(){
        super.onPause();
        hideKeyboard(requireView());
    }

    private void setUpListeners() {
        saveSongButton.setOnClickListener(this::addSong);
        editTextLyrics.addTextChangedListener(startTextWatcher());
    }

    @NotNull
    private TextWatcher startTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = editable.toString();
                int currentLength = currentText.length();
                nrOfCharsLyrics.setText(currentLength + "/100");
            }
        };
    }

    private void setUpViewItems() {
        editTextTitle = root.findViewById(R.id.title_edit_text);
        editTextArtist = root.findViewById(R.id.artist_edit_text);
        editTextLyrics = root.findViewById(R.id.lyrics_edit_text);
        nrOfCharsLyrics = root.findViewById(R.id.max_chars_count);
        saveSongButton = root.findViewById(R.id.save_song);
    }



    private void addSong(View root){
        String title = editTextTitle.getText().toString();
        String artist = editTextArtist.getText().toString();
        String lyrics = editTextLyrics.getText().toString();

        //Trim is used to avoid accepting input from user in form of blank spaces.
        if(title.trim().isEmpty() || artist.trim().isEmpty() || lyrics.trim().isEmpty()){
            Snackbar.make(root,R.string.incorrect_song_add, Snackbar.LENGTH_SHORT).show();
            hideKeyboard(root);
            return;
        }

        addSongViewModel.insert(new Song(title,artist,lyrics,true));
        Snackbar.make(root,R.string.song_added, Snackbar.LENGTH_SHORT).show();
        editTextTitle.getText().clear();
        editTextArtist.getText().clear();
        editTextLyrics.getText().clear();

        hideKeyboard(root);
    }

    /**
     * Hides the keyboard.
     * @param view
     */
    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) requireContext().
                getSystemService(requireActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
