package com.example.android.p4musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SertanejoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        // Create a list of songs
        final ArrayList<Song> songs = new ArrayList<Song>();
        // Add songs to the list (song name, artist name, audio file)
        songs.add(new Song("Ai Se Eu Te Pego", "Michel Teló", R.raw.sertanejo_micheltelo_aiseeutepego));
        songs.add(new Song("Vem novinha", "Henrique e Juliano", R.raw.sertanejo_vemnovinha_henriqueejuliano));
        songs.add(new Song("Fiorino", "Gabriel Gava", R.raw.sertanejo_gabrielgava_fiorino));
        songs.add(new Song("Tem Que Ter Pegada", "Zé Neto e Cristiano Part. Israel Novaes", R.raw.sertanejo_zenetoecristiano_temqueterpegada));
        songs.add(new Song("So Vou Beber Mais Hoje", "Humberto e Ronaldo", R.raw.sertanejo_humbertoeronaldo_sovoubebermaishoje));
        songs.add(new Song("Louquinha", "João Lucas e Marcelo Part. MC K9", R.raw.sertanejo_louquinha_joaolucasemarcelo));
        songs.add(new Song("Não Tô Valendo Nada", "Henrique e Juliano part. João Neto e Frederico", R.raw.sertanejo_naotovalendonada_henriqueejuliano));
        songs.add(new Song("Chora Me Liga", "João Bosco e Vinícius", R.raw.sertanejo_joaoboscoevinicius_chorameliga));
        songs.add(new Song("Gatinha Assanhada", "Gusttavo Lima", R.raw.sertanejo_gusttavolima_gatinhaassanhada));
        songs.add(new Song("Arrocha Com Tequila", "João Pedro e Felipe part. Cristiano Araújo", R.raw.sertanejo_arrochacomtequila_joaopedroefelipe));
        // Create a {@link SongAdapter}, whose data source is a list of Songs This list item layout contains a single
        // {@link TextView}, which the adapter will set to display a single song.
        SongAdapter adapter = new SongAdapter(this, songs);
        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}. There should be a {@link ListView} with
        // the ID called list, which is declared in song_list.xmlt file.
        ListView listView = (ListView) findViewById(R.id.list);
        // Make the {@link ListView} use the {@link SongAdapter} we created above, so that the {@link ListView} will display list
        // items for each song in the list of songs.
        listView.setAdapter(adapter);
        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // The code in this method will be executed when a forro song view is clicked on.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nowPlayingIntent = new Intent(SertanejoActivity.this, NowPlayingActivity.class);
                nowPlayingIntent.putExtra("genre", "Sertanejo");
                nowPlayingIntent.putExtra("position", position);
                nowPlayingIntent.putExtra("genreImage", R.drawable.sertanejo);
                // Parcel the songs list to the NowPlaying Activity
                nowPlayingIntent.putParcelableArrayListExtra("list", songs);
                startActivity(nowPlayingIntent);
            }
        });
    }
}
