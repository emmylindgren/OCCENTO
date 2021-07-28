package se.umu.emli.ou3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SongViewModel extends AndroidViewModel {
    private SongRepository repository;
    private LiveData<List<Song>> allSongs;

    public SongViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new SongRepository(application);
        allSongs = repository.getAllSongs();
    }

    public void insert(Song song){ repository.insert(song);}

    public void update(Song song){ repository.update(song);}

    public void delete(Song song){ repository.delete(song);}

    public LiveData<List<Song>> getAllSongs(){ return allSongs;}
}