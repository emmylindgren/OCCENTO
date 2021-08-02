package se.umu.emli.ou3.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import se.umu.emli.ou3.SongRepository;
import se.umu.emli.ou3.ui.addSong.AddSongFragment;

/**
 * ViewModel class.
 *
 * Holding and preparing all the data for the userinterface {@link HomeFragment}. Communicates
 * with the repository, passing information between UI and model classes. Also survives configuration
 * changes like rotations of the screen.
 * TODO: mer kommentarer. Ovan är från yt videon.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class HomeViewModel extends ViewModel {

    public HomeViewModel() {
        super();
    }


}