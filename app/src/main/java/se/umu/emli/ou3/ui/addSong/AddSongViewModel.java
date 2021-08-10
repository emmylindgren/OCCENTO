package se.umu.emli.ou3.ui.addSong;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

import se.umu.emli.ou3.Song;
import se.umu.emli.ou3.SongRepository;

/**
 * ViewModel class.
 *
 * Holding and preparing data for the userinterface {@link AddSongFragment}. Communicates
 * with the repository, passing information between UI, db and other model classes. Also survives
 * configuration changes like rotations of the screen.
 *
 * Adds songs to the db via the repository.
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

    /**
     * Adds the song to the db via the repository.
     * @param song, the song to be added to db.
     */
    public void insert(Song song){ repository.insert(song);}

}