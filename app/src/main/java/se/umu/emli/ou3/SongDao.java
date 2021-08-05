package se.umu.emli.ou3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Queue;

/**
 * Model class.
 * DAO for communication with the SQLite db {@link SongDataBase}.
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

    /**
     * Denna ej implementerad ännu. TODO: Eventuellt fixa så bara tillagda låtar syns?
     * @return
     */
    @Query("SELECT * FROM song_table WHERE addedByUser== 1 ORDER BY title DESC")
    LiveData<List<Song>> getAllUserAddedSongs();

    @Query("SELECT * FROM song_table ORDER BY RANDOM() LIMIT 1")
    Song getRandomSong();

    @Query("SELECT * FROM song_table ORDER BY RANDOM() LIMIT 10")
    List<Song> getRandomSongs();

    /**
     * Denna ej heller implementerad.
     * @return

    @Query("SELECT * FROM song_table ORDER BY RANDOM() LIMIT 5")
    Queue<Song> randoms();



    @Query("SELECT * FROM song_table LIMIT 1 OFFSET :randomNum")
    Song getRandomSongs(int randomNum);
    //public List<Song> getRandomSongs();
     */

}
