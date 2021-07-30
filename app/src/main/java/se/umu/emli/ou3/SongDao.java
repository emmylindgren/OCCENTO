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

    @Query("SELECT * FROM song_table ORDER BY title DESC")
    LiveData<List<Song>> getAllSongs();

    /**
     * Denna ej implementerad ännu. TODO: Eventuellt fixa så bara tillagda låtar syns?
     * @return
     */
    @Query("SELECT * FROM song_table WHERE addedByUser== 1 ORDER BY title DESC")
    LiveData<List<Song>> getAllUserAddedSongs();

}
