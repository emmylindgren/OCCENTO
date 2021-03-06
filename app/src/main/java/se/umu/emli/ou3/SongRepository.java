package se.umu.emli.ou3;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

    /**
     * Checks if DB is empty. Gets a song from DB and if the song is null then the DB is empty.
     *
     * Has to run on a Callable thread as a future task because it does not
     * return livedata, and thus have to make sure operation is completed before returning a song
     * from the DB.
     * @return boolean stating if DB is empty or not.
     */
    public boolean isSongDBEmpty(){
        Callable callable = new GetASongTask(songDao);
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future futureTask = service.submit(callable);
        Song aSong = null;
        try {
            aSong = (Song)futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            service.shutdown();
        }

        return (aSong == null);
    }

    /**
     * Get random Songs from the DB in form of a Queue.
     *
     * Has to run on a Callable thread as a future task because it does not
     * return livedata, and thus have to make sure operation is completed before returning a
     * Queue of songs from the DB.
     * @return A queue of random songs from the DB.
     */
    public Queue<Song> getRandomSongs(){
        Callable callable = new GetRandomsTask(songDao);
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future futureTask = service.submit(callable);
        Queue<Song> randomSongsQueue = null;
        try {
            randomSongsQueue = (Queue<Song>) futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            service.shutdown();
        }

        return randomSongsQueue;
    }

    public LiveData<List<Song>> getAllSongs(){
        return allSongs;
    }

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

    /**
     * Task for getting Random songs from DB in form of a list of songs.
     * Before returning songs they are converted into a linked list, a Queue.
     */
    private static class GetRandomsTask implements Callable<Queue<Song>> {
        private SongDao songDao;

        public GetRandomsTask(SongDao songDao){
            this.songDao = songDao;
        }

        @Override
        public Queue<Song> call() throws Exception {
            List<Song> randomSongsList = songDao.getRandomSongs();
            return new LinkedList<>(randomSongsList);
        }
    }

    private static class GetASongTask implements Callable<Song>{
        private SongDao songDao;

        public GetASongTask(SongDao songDao){
            this.songDao = songDao;
        }

        @Override
        public Song call() throws Exception {
            return songDao.getASong();
        }
    }
}
