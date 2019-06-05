package swt6.spring.worklog.config;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import swt6.spring.worklog.domain.*;
import swt6.spring.worklog.logic.WorkLogFacade;
import swt6.util.SongDuration;

public class DatabaseInitializer implements CommandLineRunner {

  @Autowired
  private WorkLogFacade workLog;

  @Override
  public void run(String... args) throws Exception {
    
    Employee empl1 = new Employee("Sepp", "Forcher", LocalDate.of(1935, 12, 12));
    empl1.addLogbookEntry(new LogbookEntry("Jour Fixe", LocalDateTime.of(2018, 3, 1, 8, 15), LocalDateTime.of(2018, 3, 1, 10, 0)));
    empl1.addLogbookEntry(new LogbookEntry("Analyse", LocalDateTime.of(2018, 3, 1, 10, 0), LocalDateTime.of(2018, 3, 1, 13, 45)));
    empl1.addLogbookEntry(new LogbookEntry("Implementierung", LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30)));
    workLog.syncEmployee(empl1);
    
    Employee empl2 = new Employee("Alfred", "Kunz", LocalDate.of(1944, 8, 10));
    empl2.addLogbookEntry(new LogbookEntry("Jour Fixe", LocalDateTime.of(2018, 3, 1, 8, 15), LocalDateTime.of(2018, 3, 1, 10, 0)));
    empl2.addLogbookEntry(new LogbookEntry("Unit-Test schreiben", LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30)));
    empl2.addLogbookEntry(new LogbookEntry("Integations-Tests wiederholen", LocalDateTime.of(2018, 3, 1, 14, 30), LocalDateTime.of(2018, 3, 1, 16, 00)));
    workLog.syncEmployee(empl2);
    
    Employee empl3 = new Employee("Sigfried", "Hinz", LocalDate.of(1954, 5, 3));
    empl3.addLogbookEntry(new LogbookEntry("Jour Fixe", LocalDateTime.of(2018, 3, 1, 8, 15), LocalDateTime.of(2018, 3, 1, 10, 0)));
    empl3.addLogbookEntry(new LogbookEntry("Benutzerdoku aktualisieren", LocalDateTime.of(2018, 3, 1, 8, 15), LocalDateTime.of(2018, 3, 1, 16, 30)));
    workLog.syncEmployee(empl3);


    Genre rock = new Genre("ROCK");
    Genre pop = new Genre("POP");
    Genre indie = new Genre("INDIE");

    /*---------------------Album1---------------------*/
    Album a1 = new Album("Highway to Hell",1979);

    SongDuration sd1 = new SongDuration(3,32);
    Song s1 = new Song("Girls Got Rhythm", sd1, "ACDC");
    rock.addSong(s1);
    a1.addSong(s1);


    SongDuration sd2 = new SongDuration(3,28);
    Song s2 = new Song("Highway to Hell", sd2, "ACDC");
    rock.addSong(s2);
    a1.addSong(s2);

    Song s3 = new Song(
            "Walk All Over You",
            new SongDuration(5,9),
            "ACDC"
    );
    rock.addSong(s3);
    a1.addSong(s3);

    workLog.syncAlbum(a1);

    /*---------------------Album2---------------------*/
    Album a2 = new Album("Backstreet's Back",1997);

    SongDuration sd3 = new SongDuration(4,21);
    Song s4 = new Song("Like a Child", sd3, "Backstreet Boys");
    pop.addSong(s4);
    a2.addSong(s4);

    SongDuration sd4 = new SongDuration(3,25);
    Song s5 = new Song("Everybody", sd4, "Backstreet Boys");
    pop.addSong(s5);
    a2.addSong(s5);


    Song s6 = new Song(
            "That's the Way I Like It",
            new SongDuration(3,9),
            "Backstreet Boys"
    );
    pop.addSong(s6);
    a2.addSong(s6);
    workLog.syncAlbum(a2);

    /*---------------------Album3---------------------*/
    Album a3 = new Album("The Queen Is Dead", 1986);
    Song s7 = new Song(
            "The Queen Is Dead",
            new SongDuration(5,11),
            "The Smiths"
    );
    indie.addSong(s7);
    a3.addSong(s7);

    Song s8 = new Song(
            "Never Had No One Ever",
            new SongDuration(5,17),
            "The Smiths"
    );
    indie.addSong(s8);
    a3.addSong(s8);

    Song s9 = new Song(
            "Frankly, Mr Shankly",
            new SongDuration(3,30),
            "The Smiths"
    );
    indie.addSong(s9);
    a3.addSong(s9);
    workLog.syncAlbum(a3);

  }
}