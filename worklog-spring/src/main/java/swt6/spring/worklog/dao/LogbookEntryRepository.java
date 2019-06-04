package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.LogbookEntry;

@Repository //stereotype: was für eine art die komponente ist zB. Component, Component würde auch funkt. -> spring entscheidet
public interface LogbookEntryRepository extends JpaRepository<LogbookEntry, Long> {

}
