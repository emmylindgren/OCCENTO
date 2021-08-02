package se.umu.emli.ou3;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Model class.
 * SQLite Room database for containing Song entities.
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */

@Database(entities = Song.class, version = 1)
public abstract class SongDataBase extends RoomDatabase {

    private static SongDataBase instance;

    public abstract SongDao songdao();

    /**
     * Constructor is a singleton to make sure only one instance of the database is initiated.
     * Only creates a database if one is not already created.
     * Syncronized to make sure only one thread at a time can access this method and thus makes
     * sure only one instance is created.
     * @param context
     * @return the db created.
     */
    public static synchronized SongDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SongDataBase.class, "song_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        // Is called when the Database is created.
        @Override
        public void onCreate(@NonNull @org.jetbrains.annotations.NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Thread(new PopulateDbTask(instance)).start();
        }
    };

    /**
     * Populates the database with Songs.
     */
    private static class PopulateDbTask implements Runnable{
        private SongDao songDao;

        private PopulateDbTask(SongDataBase db){
            songDao = db.songdao();
        }

        @Override
        public void run() {
            //TODO: Lägg till fler låtar här.
            songDao.insert(new Song("Sång1","Artist1","Bla bla bla",false));

        }
    }
}
