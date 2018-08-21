package com.example.android.p4musicalstructure;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    /**
     * Create an object to help making Song class parcelable
     */
    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
    /**
     * Song name
     */
    private String mSongName;
    /**
     * Artist name
     */
    private String mSongArtist;
    /**
     * Audio resource ID for the song
     */
    private int mAudioResourceId;

    public Song(String songName, String artistName, int audioResourceId) {
        mSongName = songName;
        mSongArtist = artistName;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Constructor with a Parcel parameter for the returned Song object from createFromParcel method
     */
    public Song(Parcel source) {
        mSongName = source.readString();
        mSongArtist = source.readString();
        mAudioResourceId = source.readInt();
    }

    /**
     * Return the song name of the song
     */
    public String getSongName() {
        return mSongName;
    }

    /**
     * Return the artist name of the song
     */
    public String getArtistName() {
        return mSongArtist;
    }

    /**
     * Return the audio resource ID for the song
     */
    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    /**
     * Implement Parcelable class methods to make Song class parcelable
     */
    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSongName);
        dest.writeString(mSongArtist);
        dest.writeInt(mAudioResourceId);
    }
}

