package se.umu.emli.ou3.ui.addSong;

import android.app.Application;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import se.umu.emli.ou3.Song;
import se.umu.emli.ou3.SongRepository;

/**
 * ViewModel class.
 *
 * Holding and preparing all the data for the userinterface {@link AddSongFragment}. Communicates
 * with the repository, passing information between UI, db and other model classes. Also survives
 * configuration changes like rotations of the screen.
 * TODO: mer kommentarer. Ovan är från yt videon.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class AddSongViewModel extends AndroidViewModel {

    private SongRepository repository;

    public AddSongViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new SongRepository(application);
    }



    public void insert(Song song){ repository.insert(song);}

}