package se.umu.emli.ou3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SongViewModel songViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView songRecyclerView = findViewById(R.id.song_recycler_view);
        songRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        songRecyclerView.setHasFixedSize(true);

        SongAdapter songAdapter = new SongAdapter();
        songRecyclerView.setAdapter(songAdapter);

        songViewModel = new ViewModelProvider(this).get(SongViewModel.class);
        songViewModel.getAllSongs().observe(this, songs -> songAdapter.setSongs(songs));
    }
}