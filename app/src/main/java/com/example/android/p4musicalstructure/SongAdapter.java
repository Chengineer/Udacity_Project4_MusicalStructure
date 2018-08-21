package com.example.android.p4musicalstructure;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {
    /**
     * Create a new {@link SongAdapter} object
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param songs   is the list of {@link Song}s to be displayed.
     */
    public SongAdapter(Activity context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Get the {@link Song} object located at this position in the list
        Song currentSong = getItem(position);
        // Find the TextView in the item_list.xml layout with the ID song_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.song_name);
        // Get the song name from the currentSong object and set this text on the name TextView
        nameTextView.setText(currentSong.getSongName());
        // Find the TextView in the item_list.xml layout with the ID artist_name
        TextView artistTextView = (TextView) listItemView.findViewById(R.id.artist_name);
        // Get the artist name from the currentSong object and set this text on the artist TextView
        artistTextView.setText(currentSong.getArtistName());
        // Return the whole list item layout so that it can be shown in the ListView
        return listItemView;
    }
}
