package se.umu.emli.ou3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Model class.
 * DAO for communication with the SQLite db {@link SongDataBase}. An interface making db operations
 * into methods.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
@Dao
public interface SongDao {

    @Insert
    void insert(Song song);

    @Update
    void update(Song song);

    @Delete
    void delete(Song song);

    @Query("SELECT * FROM song_table ORDER BY title DESC")
    LiveData<List<Song>> getAllSongs();


    @Query("SELECT * FROM song_table LIMIT 1")
    Song getASong();

    @Query("SELECT * FROM song_table ORDER BY RANDOM() LIMIT 10")
    List<Song> getRandomSongs();

}
