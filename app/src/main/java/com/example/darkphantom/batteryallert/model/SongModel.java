package com.example.darkphantom.batteryallert.model;




import java.io.Serializable;


public class SongModel  implements Serializable {
    private long id;
    private String title;
    private String artist;
    private String path;
    private long albumId;
    private String album;
    private long duration;
    public SongModel(long songID, String songTitle, String songArtist, String songPath, long albumId , String album, long duration) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        path = songPath;
        this.albumId = albumId;
        this.album = album;
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getPath() {return  path;}
    public long getID(){return id;}
    public String getTitl(){return title;}
    public String getArtist(){return artist;}


}
