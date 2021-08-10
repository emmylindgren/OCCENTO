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

/**
 * View class.
 *
 * Handles UI and operating system interactions for the user to be adding own songs
 * into the db. Makes sure all information needed for a song is filled in in the correct way and
 * in that case adds the song to db. In other cases a snackbar is shown to inform the user
 * to correctly fill in the information.
 *
 * A song has to contain a title, artist and lyrics. Lyrics can not be more than 100 characters and
 * AddSongFragment keeps track of how many characters is used for the user and displays it.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
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

    /**
     * Hides the keyboard in onPause() to make keyboard go away when user presses back button.
     */
    @Override
    public void onPause(){
        super.onPause();
        hideKeyboard(requireView());
    }

    /**
     * Sets up listeners for the view. In this case a listener on the "save song" button and
     * a listener on the edittext field for lyrics for counting characters for the user.
     */
    private void setUpListeners() {
        saveSongButton.setOnClickListener(this::checkSong);
        editTextLyrics.addTextChangedListener(startTextWatcher());
    }

    /**
     * Updates the view character count when user types in characters. To give user visual
     * input character counter for the lyrics field.
     * @return a new textwatcher.
     */
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
                nrOfCharsLyrics.setText(currentLength + getString(R.string.totalCharacterCount));
            }
        };
    }

    /**
     * Sets up the viewitems for the view, edit texts, textviews and the button.
     */
    private void setUpViewItems() {
        editTextTitle = root.findViewById(R.id.title_edit_text);
        editTextArtist = root.findViewById(R.id.artist_edit_text);
        editTextLyrics = root.findViewById(R.id.lyrics_edit_text);
        nrOfCharsLyrics = root.findViewById(R.id.max_chars_count);
        saveSongButton = root.findViewById(R.id.save_song);
    }

    /**
     * Checks if the song information is filled in correctly by the user, AKA the user
     * left no edittext; song, artist or lyrics empty. Hides keyboard also to give user an
     * overview of the fields.
     * @param root, the root view. In this case the fragment add song that was inflated.
     */
    private void checkSong(View root){
        String title = editTextTitle.getText().toString();
        String artist = editTextArtist.getText().toString();
        String lyrics = editTextLyrics.getText().toString();

        //Trim is used to avoid accepting input from user in form of blank spaces.
        if(title.trim().isEmpty() || artist.trim().isEmpty() || lyrics.trim().isEmpty()){
            Snackbar.make(root,R.string.incorrect_song_add, Snackbar.LENGTH_SHORT).show();
            hideKeyboard(root);
            return;
        }

        addSong(root, title, artist, lyrics);
        hideKeyboard(root);
    }

    /**
     * Adds a song to db. Notifies the user that song has been added and clears the
     * input text fields to prepare for another input from user.
     * @param root, the root view. In this case the fragment add song that was inflated.
     * @param title, title of the song to be added.
     * @param artist, the song to be added artists.
     * @param lyrics, some lyrics of the song to be added.
     */
    private void addSong(View root, String title, String artist, String lyrics) {
        addSongViewModel.insert(new Song(title, artist, lyrics,true));
        Snackbar.make(root,R.string.song_added, Snackbar.LENGTH_SHORT).show();
        editTextTitle.getText().clear();
        editTextArtist.getText().clear();
        editTextLyrics.getText().clear();
    }

    /**
     * Hides the keyboard.
     * @param view, the root view. In this case the fragment add song that was inflated.
     */
    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) requireContext().
                getSystemService(requireActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
