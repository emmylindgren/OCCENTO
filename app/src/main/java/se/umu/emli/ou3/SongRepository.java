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

    public void insert(Song song){ new Thread(new InsertTask(songDao,song)).start();}

    public void update(Song song){ new Thread(new UpdateTask(songDao,song)).start();}

    public void delete(Song song){ new Thread(new DeleteTask(songDao,song)).start();}

    public LiveData<List<Song>> getAllSongs(){
        return allSongs;
    }

    /**
     * TODO: Ändra detta.
     * Varje funktion i songdaoen måste här följas med en metod som kör på en tråd.
     * ROOM tillåter inte databasoperationer på Main-tråden pga det kan få vår app att hänga
     * sig & krascha.
     */
    private static class InsertTask implements Runnable{
        private SongDao songDao;
        private Song song;

        public InsertTask(SongDao songDao, Song song){
            this.songDao = songDao;
            this.song = song;
        }

        @Override
        public void run() {
            songDao.insert(song);
        }
    }

    private static class UpdateTask implements Runnable{
        private SongDao songDao;
        private Song song;

        public UpdateTask(SongDao songDao, Song song){
            this.songDao = songDao;
            this.song = song;
        }

        @Override
        public void run() {
            songDao.update(song);
        }
    }

    private static class DeleteTask implements Runnable{
        private SongDao songDao;
        private Song song;

        public DeleteTask(SongDao songDao, Song song){
            this.songDao = songDao;
            this.song = song;
        }

        @Override
        public void run() {
            songDao.delete(song);
        }
    }
}
