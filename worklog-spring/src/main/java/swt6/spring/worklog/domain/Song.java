package swt6.spring.worklog.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import swt6.util.SongDuration;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Song implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private SongDuration duration;
    private String interpreter;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
                optional = false)
    private Genre genre;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SONG_ALBUM",
        joinColumns = @JoinColumn(name = "ALBUM_ID"),
        inverseJoinColumns = @JoinColumn(name = "SONG_ID"))
    private Set<Album> albums = new HashSet<>();

    public Song(){}

    public Song(String title, SongDuration duration, String interpreter) {
        this.title = title;
        this.duration = duration;
        this.interpreter = interpreter;
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

    public SongDuration getDuration() {
        return duration;
    }

    public void setDuration(SongDuration duration) {
        this.duration = duration;
    }

    public String getInterpreter() {
        return interpreter;
    }

    public void setInterpreter(String interpreter) {
        this.interpreter = interpreter;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void detachGenre(){
        if(this.genre != null) this.genre.getSongs().remove(this);
        this.genre = null;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album album){
        if(album == null)
            throw new IllegalArgumentException("album is null");
        album.getSongs().add(this);
        this.albums.add(album);
    }

    public void removeAlbum(Album album){
        if(album == null)
            throw new IllegalArgumentException("Album is null");
        album.getSongs().remove(this);
        this.albums.remove(album);
    }
}
