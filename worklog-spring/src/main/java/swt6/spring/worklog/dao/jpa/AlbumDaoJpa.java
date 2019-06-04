package swt6.spring.worklog.dao.jpa;

import org.springframework.dao.DataAccessException;
import swt6.spring.worklog.dao.AlbumDao;
import swt6.spring.worklog.domain.Album;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AlbumDaoJpa implements AlbumDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Album findById(Long aLong) {
        return null;
    }

    @Override
    public List<Album> findAll() {
        return em.createQuery("select a from Album a", Album.class).getResultList();
    }

    @Override
    public void insert(Album entity) {
        Album album = em.merge(entity);
        entity.setId(album.getId());
    }

    @Override
    public Album merge(Album entity) throws DataAccessException {
        return em.merge(entity);
    }

}
