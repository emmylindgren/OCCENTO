package se.umu.emli.ou3;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class.
 * SQLite Room database for containing Song entities.
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */

@Database(entities = Song.class, version = 1)
public abstract class SongDataBase extends RoomDatabase {

    private static SongDataBase instance;
    private static Context context;
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
        SongDataBase.context = context;
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
            new Thread(new PopulateDbTask(instance, context)).start();
        }
    };

    /**
     * Populates the database with Songs.
     */
    private static class PopulateDbTask implements Runnable{
        private SongDao songDao;
        private Context context;

        private PopulateDbTask(SongDataBase db, Context context){
            songDao = db.songdao();
            this.context = context;
        }

        @Override
        public void run() {

            Gson gson = new Gson();

            try {
                InputStream inputStream = context.getAssets().open("songs.json");
                InputStreamReader inputStreamReader =new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                List<Song> dbSongs = gson.fromJson(reader,new TypeToken<List<Song>>(){}.getType());

                for (Song song:dbSongs) {
                    songDao.insert(song);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
