package swt6.spring.worklog.dao;

import swt6.spring.worklog.domain.Song;

import java.util.List;

public interface SongDao extends GenericDao<Song, Long> {
    List<Song> findSongsByAlbumId(long id);
}
