package se.umu.emli.ou3;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * TODO: Kommentera mer här.
 *
 */
@Database(entities = Song.class, version = 1)
public abstract class SongDataBase extends RoomDatabase {

    private static SongDataBase instance;

    public abstract SongDao songdao();

    /**
     * TODO: Kommentera mera här.
     * Is a singleton to make sure only one instance of the database is initiated. Only creates a
     * database if one is not already created.
     * Syncronized to make sure only one thread at a time can access this method and thus makes
     * sure only one instance is created.
     * @param context
     * @return
     */
    public static synchronized SongDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SongDataBase.class, "song_database").fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
