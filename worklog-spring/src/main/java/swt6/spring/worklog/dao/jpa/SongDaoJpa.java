package swt6.spring.worklog.dao.jpa;

import org.springframework.stereotype.Repository;
import swt6.spring.worklog.dao.SongDao;
import swt6.spring.worklog.domain.Song;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class SongDaoJpa implements SongDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Song findById(Long aLong) {
        return null;
    }

    @Override
    public List<Song> findAll() {
        return null;
    }

    @Override
    public void insert(Song entity) {
        Song persSong = em.merge(entity);
        entity.setId(persSong.getId());
    }

    @Override
    public Song merge(Song entity) {
        return null;
    }

    @Override
    public List<Song> findSongsByAlbumId(long id) {
        return em.createQuery("select s from Song s where s.albums.id=:id", Song.class)
                .setParameter("id", id)
                .getResultList();
    }
}
