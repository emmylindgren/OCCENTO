package se.umu.emli.ou3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SongDao {

    @Insert
    void insert(Song song);

    @Update
    void update(Song song);

    @Delete
    void delete(Song song);

    /* TODO: Ta bort denna. Till för att ta bort alla.
    @Query("DELETE FROM song_table")
    void deleteAllSongs();
     */

    @Query("SELECT * FROM song_table ORDER BY title DESC")
    LiveData<List<Song>> getAllSongs();

}
