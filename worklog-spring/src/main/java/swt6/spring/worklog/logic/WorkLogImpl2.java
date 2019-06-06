package swt6.spring.worklog.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.AlbumRepository;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.dao.LogbookEntryRepository;
import swt6.spring.worklog.dao.SongRepository;
import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Song;

import java.util.List;

@Component("workLog")
@Primary // if two beans with name "workLog" exist in app context use this one
@Transactional
public class WorkLogImpl2 implements WorkLogFacade {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LogbookEntryRepository logbookEntryRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void setLogbookEntryRepository(LogbookEntryRepository logbookEntryRepository) {
        this.logbookEntryRepository = logbookEntryRepository;
    }

    public void setAlbumRepository(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public void setSongRepository(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public LogbookEntry syncLogbookEntry(LogbookEntry entry) {
        return logbookEntryRepository.saveAndFlush(entry);
    }

    @Override
    public LogbookEntry findLogbookEntryById(Long id) {
        return logbookEntryRepository.findById(id).get();
    }

    @Override
    public void deleteLogbookEntryById(Long id) {
        logbookEntryRepository.deleteById(id);
    }

    @Override
    public Album syncAlbum(Album album) {
        return albumRepository.saveAndFlush(album);
    }

    @Override
    public List<Album> findAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public Album findAlbumById(Long id) {
        return albumRepository.findById(id).get();
    }

    @Override
    public List<Song> findSongsByAlbumId(long id) {
        return null;
    }

    @Override
    public Song syncSong(Song song) {
        return songRepository.saveAndFlush(song);
    }

    @Override
    public Song findSongById(Long id) {
        return songRepository.findById(id).get();
    }


}
