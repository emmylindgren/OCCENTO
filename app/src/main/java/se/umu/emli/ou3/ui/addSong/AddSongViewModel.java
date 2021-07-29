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

public class AddSongViewModel extends AndroidViewModel {

    private SongRepository repository;

    public AddSongViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new SongRepository(application);
    }

    public void insert(Song song){ repository.insert(song);}

}