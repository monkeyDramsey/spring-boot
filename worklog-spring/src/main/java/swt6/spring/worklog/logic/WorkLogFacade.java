package swt6.spring.worklog.logic;

import java.util.List;

import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.domain.Song;

public interface WorkLogFacade {
  public Employee       syncEmployee(Employee employee);
  public Employee       findEmployeeById(Long id);
  public List<Employee> findAllEmployees();
  
  public LogbookEntry   syncLogbookEntry(LogbookEntry entry);
  public LogbookEntry   findLogbookEntryById(Long id);
  public void           deleteLogbookEntryById(Long id);

  public Album          syncAlbum(Album album);
  public List<Album>    findAllAlbums();
  public Album          findAlbumById(Long id);

  public List<Song>     findSongsByAlbumId(long id);
  public Song           syncSong(Song song);
  public Song           findSongById(Long id);
}