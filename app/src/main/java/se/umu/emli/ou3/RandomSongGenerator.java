package se.umu.emli.ou3;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import java.util.Queue;

/**
 * A class for generating random songs from db.
 * Gets a queue of 10 random songs from db, polls the queue for random song until it's empty
 * and then refills queue with 10 new random songs.
 */
public class RandomSongGenerator {

    private Queue<Song> randomSongQueue;
    private SongRepository repository;

    public RandomSongGenerator(Application application){
        repository = new SongRepository(application);
        randomSongQueue = repository.getRandomSongs();
    }

    public Song getRandomSong(){
        if(randomSongQueue.isEmpty()){
            randomSongQueue = repository.getRandomSongs();
        }

        return randomSongQueue.poll();
    }

    //TODO: Ta bort denna
    public Boolean doesSongExists(){
        return !(repository.getRandomSongs().isEmpty());
    }


}
