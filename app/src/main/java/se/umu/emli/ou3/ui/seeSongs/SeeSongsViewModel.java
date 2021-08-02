package se.umu.emli.ou3.ui.seeSongs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import se.umu.emli.ou3.Song;
import se.umu.emli.ou3.SongRepository;
import se.umu.emli.ou3.ui.addSong.AddSongFragment;

/**
 * ViewModel class.
 *
 * Holding and preparing all the data for the userinterface {@link SeeSongsFragment}. Communicates
 * with the repository, passing information between UI, db and other model classes. Also survives
 * configuration changes like rotations of the screen.
 * TODO: mer kommentarer. Ovan är från yt videon.
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

    public void insert(Song song){ repository.insert(song);}

    public void update(Song song){ repository.update(song);}

    public void delete(Song song){ repository.delete(song);}

    public LiveData<List<Song>> getAllSongs(){ return allSongs;}
}