package se.umu.emli.ou3.ui.seeSongs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import se.umu.emli.ou3.Song;
import se.umu.emli.ou3.SongRepository;

/**
 * ViewModel class.
 *
 * Holding and preparing all the data for the userinterface {@link SeeSongsFragment}. Communicates
 * with the repository, passing information between UI and DB. Also survives
 * configuration changes like rotations of the screen. Provides the fragment with livedata with
 * list of songs in DB and also deletes songs from DB.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class SeeSongsViewModel extends AndroidViewModel {

    private SongRepository repository;
    private LiveData<List<Song>> allSongs;

    public SeeSongsViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new SongRepository(application);
        allSongs = repository.getAllSongs();
    }

    /**
     * Deletes a specific song from DB.
     * @param song to be deleted.
     */
    public void delete(Song song){ repository.delete(song);}

    /**
     * Fetching liveData from DB with list of songs.
     * @return Livedata with list of songs in DB. 
     */
    public LiveData<List<Song>> getAllSongs(){ return allSongs;}
}