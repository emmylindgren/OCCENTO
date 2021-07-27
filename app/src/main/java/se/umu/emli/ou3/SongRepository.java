package se.umu.emli.ou3;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SongRepository {
    private SongDao songDao;
    private LiveData<List<Song>> allSongs;

    public SongRepository(Application application){
        SongDataBase dataBase = SongDataBase.getInstance(application);
        songDao = dataBase.songdao();
        allSongs = songDao.getAllSongs();

    }

    public void insert(Song song){
        new Thread(new Insert(songDao,song)).start();
    }

    public void update(Song song){

    }
    public void delete(Song song){

    }
    public LiveData<List<Song>> getAllSongs(){
        return allSongs;
    }

    private static class Insert implements Runnable{

        private SongDao songDao;
        private Song song;

        @Override
        public void run() {
            songDao.insert(song);
        }

        public Insert(SongDao songDao, Song song){
            this.songDao = songDao;
            this.song = song;
        }
    }
}
