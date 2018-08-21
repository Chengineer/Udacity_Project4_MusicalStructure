package com.example.android.p4musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CapoeiraActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        // Create a list of songs
        final ArrayList<Song> songs = new ArrayList<Song>();
        // Add songs to the list (song name, artist name, audio file)
        songs.add(new Song("Capoeira E Ligeira", "Mestre Suassuna", R.raw.capoeira_capoeiraeligeira_mestresuassuna));
        songs.add(new Song("Capoeira Não É Ninja", "Mestre Fanho", R.raw.capoeira_capoeiranaoeninja));
        songs.add(new Song("Sem Capoeira Eu Não Vivo", "Mestrando Charm", R.raw.capoeira_semcapoeiraeunaovivo_mestrandocharm));
        songs.add(new Song("Paraneuê Paraná", "Grupo Muzenza de Capoeira", R.raw.capoeira_paranaueparana));
        songs.add(new Song("Topei Quero Ver Cair", "Mestre Suassuna e Mestre Acordeon", R.raw.capoeira_querovercair_mestresuassuna));
        songs.add(new Song("Capoeira De São Salvador", "Mestre Suassuna e Dirceu", R.raw.capoeira_capoeiradesaosalvador_capoeirabrasil));
        songs.add(new Song("Lá Lauê", "Mestre Barrão e Contramestre Testa", R.raw.capoeira_mestrebarrao_lalaue));
        songs.add(new Song("Besouro Preto (Santo Amaro)", "Mestre Mão Branca", R.raw.capoeira_besouropreto_mestremaobranca));
        songs.add(new Song("Me Leva Na Bahia", "Mestrando Duende", R.raw.capoeira_melevaprabahia_mestrandoduende));
        songs.add(new Song("Na Beira Do Cais", "Mestre Fanho", R.raw.capoeira_nabeiradocais_mestrefanho));
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
            // The code in this method will be executed when a capoeira song view is clicked on.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nowPlayingIntent = new Intent(CapoeiraActivity.this, NowPlayingActivity.class);
                nowPlayingIntent.putExtra("genre", "Capoeira");
                nowPlayingIntent.putExtra("position", position);
                nowPlayingIntent.putExtra("genreImage", R.drawable.capoeira);
                // Parcel the songs list to the NowPlaying Activity
                nowPlayingIntent.putParcelableArrayListExtra("list", songs);
                startActivity(nowPlayingIntent);
            }
        });
    }
}
