package com.example.android.p4musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SambaDeGafieiraActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        // Create a list of songs
        final ArrayList<Song> songs = new ArrayList<Song>();
        // Add songs to the list (song name, artist name, audio file)
        songs.add(new Song("Cadê o Meu Amor?", "Douglas Mar", R.raw.samba_cadeomeuamor_douglasmar));
        songs.add(new Song("Meu Lugar", "Arlindo Cruz", R.raw.samba_arlindocruz_meulugar));
        songs.add(new Song("Minissaia", "Gabriel Moura", R.raw.samba_minissaia_gabrielmoura));
        songs.add(new Song("Quem Vai Chorar Sou Eu", "Diogo Nogueira part. Zeca Pagodinho", R.raw.samba_diogonogueira_quemvaichorarsoueu));
        songs.add(new Song("Canto de Ossanha", "Banda Signus", R.raw.samba_cantodeossanha_bandasignus));
        songs.add(new Song("Eu Canto Samba", "Gabriel Moura", R.raw.samba_gabrielmoura_eucantosamba));
        songs.add(new Song("Inaraí", "Katinguelê", R.raw.samba_katinguele_inarai));
        songs.add(new Song("Casal Perfeito", "Leci Brandão", R.raw.samba_casalperfeito_lecibrandao));
        songs.add(new Song("Fé Em Deus", "Diogo Nogueira", R.raw.samba_feemdeus_diogonogueira));
        songs.add(new Song("O Descobridor Dos Sete Mares", "Ive", R.raw.samba_descobridordossetemares_ive));
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
            // The code in this method will be executed when a samba song view is clicked on.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nowPlayingIntent = new Intent(SambaDeGafieiraActivity.this, NowPlayingActivity.class);
                nowPlayingIntent.putExtra("genre", "Samba de Gafieira");
                nowPlayingIntent.putExtra("position", position);
                nowPlayingIntent.putExtra("genreImage", R.drawable.dancadesalao);
                // Parcel the songs list to the NowPlaying Activity
                nowPlayingIntent.putParcelableArrayListExtra("list", songs);
                startActivity(nowPlayingIntent);
            }

        });
    }
}
