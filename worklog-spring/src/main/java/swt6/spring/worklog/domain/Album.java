package swt6.spring.worklog.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Album implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Column(name = "releaseYear")
    private int year;

    @ManyToMany(mappedBy = "albums", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Song> songs = new HashSet<>();

    public Album(){}

    public Album(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song){
        if(song == null)
            throw new IllegalArgumentException("Song is null");
        song.getAlbums().add(this);
        this.songs.add(song);
    }

    public void removeSong(Song song){
        if(song == null)
            throw new IllegalArgumentException("Song is null");
        song.getAlbums().remove(this);
        this.songs.remove(song);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
}
