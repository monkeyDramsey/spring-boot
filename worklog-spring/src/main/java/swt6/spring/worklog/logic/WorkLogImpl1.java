package swt6.spring.worklog.logic;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.AlbumDao;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.LogbookEntryDao;
import swt6.spring.worklog.dao.SongDao;
import swt6.spring.worklog.dao.jpa.AlbumDaoJpa;
import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Song;

import java.util.List;

//@Component("workLog")
//@Primary
@Transactional //aus org.springframework //sucht nach bean das transactionManager heißt -> id="transactionManager" Zeile 60
public class WorkLogImpl1 implements WorkLogFacade {

    private EmployeeDao employeeDao;
    private LogbookEntryDao logbookEntryDao;

    private AlbumDao albumDao;
    private SongDao songDao;

    // ===================== DAO Setters ====================

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setLogbookEntryDao(LogbookEntryDao logbookEntryDao) {
        this.logbookEntryDao = logbookEntryDao;
    }

    public void setAlbumDao(AlbumDao albumDao){this.albumDao = albumDao;}

    public void setSongDao(SongDao songDao){this.songDao = songDao;}


    // ===================== DAO Setters ====================

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeDao.merge(employee);
    }

    @Override
    public Album syncAlbum(Album album) {return albumDao.merge(album);}

    @Override
    public List<Album> findAllAlbums() {
        return albumDao.findAll();
    }

    @Override
    public Album findAlbumById(Long id) {
        return null;
    }

    @Override
    public List<Song> findSongsByAlbumId(long id) {
        return songDao.findSongsByAlbumId(id);
    }

    @Override
    public Song syncSong(Song song) {
        return null;
    }

    @Override
    public Song findSongById(Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeById(Long id) {
        return employeeDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeDao.findAll();
    }

    @Override
    public LogbookEntry syncLogbookEntry(LogbookEntry entry) {
        return logbookEntryDao.merge(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public LogbookEntry findLogbookEntryById(Long id) {
        return logbookEntryDao.findById(id);
    }

    @Override
    public void deleteLogbookEntryById(Long id) {
        logbookEntryDao.deleteById(id);
    }
}
