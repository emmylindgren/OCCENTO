package se.umu.emli.ou3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    private List<Song> songs = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item,
                parent,false);
        return new SongHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SongAdapter.SongHolder holder, int position) {
        Song currentSong = songs.get(position);
        holder.title.setText(currentSong.getTitle());
        holder.artist.setText(currentSong.getArtist());
        holder.lyrics.setText(currentSong.getLyrics());
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public void setSongs(List<Song> songs){
        this.songs = songs;
        notifyDataSetChanged();
    }

    class SongHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView artist;
        private TextView lyrics;

        public SongHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textview_song_title);
            artist = itemView.findViewById(R.id.textview_song_artist);
            lyrics = itemView.findViewById(R.id.textview_song_lyrics);
        }
    }
}
