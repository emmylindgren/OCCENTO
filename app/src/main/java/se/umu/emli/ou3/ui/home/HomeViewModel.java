package se.umu.emli.ou3.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

import se.umu.emli.ou3.SongRepository;

/**
 * ViewModel class.
 *
 * Preparing all the data for the userinterface {@link HomeFragment}. Communicates
 * with the repository, passing information between UI and model classes. Also survives configuration
 * changes like rotations of the screen.
 * Starts up DB when created, and tells the fragment if the DB is empty.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class HomeViewModel extends AndroidViewModel {

    private SongRepository repository;
    private Application application;

    public HomeViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.application = application;
        startUpDB();
    }

    /**
     * Starts up the Song DB by creating a repository. DB is NOT created until you perform a
     * concrete operation on it, such as invoking a @Dao method that hits the database. Therefore
     * a call is made to perform an operation it so that DB is created.
     */
    private void startUpDB(){
        repository = new SongRepository(application);
        repository.isSongDBEmpty();
    }

    /**
     * Checks if the DB is empty.
     * @return boolean if DB is empty or not.
     */
    public boolean DBIsEmpty(){
        return repository.isSongDBEmpty();
    }


}