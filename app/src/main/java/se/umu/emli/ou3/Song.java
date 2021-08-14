package se.umu.emli.ou3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Model class.
 * Entity Song. Representing a table in the SQLite db {@link SongDataBase}. Containing a primary
 * key for the entity, and the song consists of a title, artist, lyrics and a boolean for if it's
 * added by the user or by the database on startup.
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
@Entity(tableName = "song_table")
public class Song {

    /**
     * Autogenerate key to make sure no key is the same.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String artist;
    private String lyrics;
    private boolean addedByUser;

    public Song(String title, String artist, String lyrics, boolean addedByUser) {
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
        this.addedByUser = addedByUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getLyrics() {
        return lyrics;
    }

    public boolean isAddedByUser() {
        return addedByUser;
    }
}
