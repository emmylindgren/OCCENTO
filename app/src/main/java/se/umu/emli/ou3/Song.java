package se.umu.emli.ou3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "song_table")
public class Song {

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
