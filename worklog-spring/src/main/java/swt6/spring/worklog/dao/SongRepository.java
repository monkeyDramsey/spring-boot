package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Song;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

}
