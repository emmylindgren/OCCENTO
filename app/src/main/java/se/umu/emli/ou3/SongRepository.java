package se.umu.emli.ou3;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Random;

/**
 * Model class.
 * Repository for interaction between Viewmodels and db. Gives Viewmodels a single access
 * point while itself can interact with several db and sources, like web services.
 *
 * Contains methods for every database operation established in the DAO {@link SongDao} which
 * starts a new thread for calling the operation. This is because Room doesn't allow database
 * operations on the main thread.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
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
     * TODO: är de såhär man gör? haha
     * @return
     */
    public Song getRandomSong(){
        RandomTask task;
        new Thread(task = new RandomTask(songDao)).start();
        return task.get();}

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

    private static class RandomTask implements Runnable{

        private SongDao songDao;
        private Song song;

        public RandomTask(SongDao songDao){
            this.songDao = songDao;
        }

        @Override
        public void run() {
            song = songDao.getRandomSong();
        }

        public Song get(){
            return song;
        }
    }
}
