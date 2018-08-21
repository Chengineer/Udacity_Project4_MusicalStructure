package com.example.android.p4musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ForroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        // Create a list of songs
        final ArrayList<Song> songs = new ArrayList<Song>();
        // Add songs to the list (song name, artist name, audio file)
        songs.add(new Song("Xinxim No Xenhenhem", "Quarteto Olinda", R.raw.forro_quartetoolinda_xinximnoxenhenhem));
        songs.add(new Song("Prá Não Ficar Na Mão", "Conterrâneos", R.raw.forro_conterraneos_pranaoficarnamao));
        songs.add(new Song("Toque Toque Sanfoneiro", "Chico Pessoa", R.raw.forro_toquetoquesanfoneiro_chicopessoa));
        songs.add(new Song("Xamego-Penerô Xerém", "Trio Dona Zefa", R.raw.forro_triodonazefa_xamegopeneroxerem));
        songs.add(new Song("Tudo Te Dou", "Mestrinho", R.raw.forro_mestrinho_tudotedou));
        songs.add(new Song("Corra Corra", "Aviões do Forró", R.raw.forro_avioesdoforro_corracorra));
        songs.add(new Song("Xaxado Bamba", "Trio Dona Zefa", R.raw.forro_xaxadobamba_triodonazefa));
        songs.add(new Song("Mexe Mexe", "Coisa de Zé", R.raw.forro_coisadeze_mexemexe));
        songs.add(new Song("Forró Do apagão", "Mestre Zinho", R.raw.forro_mestrezinho_forrodoapagao));
        songs.add(new Song("Canto Do Sabiá", "Trio Dona Zefa", R.raw.forro_cantodosabia_triodonazefa));
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
                Intent nowPlayingIntent = new Intent(ForroActivity.this, NowPlayingActivity.class);
                nowPlayingIntent.putExtra("genre", "Forró");
                nowPlayingIntent.putExtra("position", position);
                nowPlayingIntent.putExtra("genreImage", R.drawable.forroz);
                // Parcel the songs list to the NowPlaying Activity
                nowPlayingIntent.putParcelableArrayListExtra("list", songs);
                startActivity(nowPlayingIntent);
            }
        });
    }
}
