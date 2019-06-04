package swt6.spring.worklog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import swt6.spring.worklog.domain.*;
import swt6.spring.worklog.logic.WorkLogFacade;
import swt6.util.LocalDateTimeEditor;
import swt6.util.SongDuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class SongController {

    private final Logger logger = LoggerFactory.getLogger(SongController.class);


    @Autowired
    private WorkLogFacade workLogFacade;

    @RequestMapping("/albums/{albumId}/songs")
    public String list(@PathVariable("albumId") long albumId, Model model){
        Album album = workLogFacade.findAlbumById(albumId);
        logger.debug("albumId {}", albumId);
        logger.debug("album {}", album);
        List<Song> songs = new ArrayList<>(album.getSongs());
        logger.debug("songs view for album {}", album.getSongs());

        model.addAttribute("album", album);
        model.addAttribute("songs", songs);

        return "songList";
    }

    @RequestMapping(value="/employees/{employeeId}/entries/new", method = RequestMethod.GET)
    public String initNew(@PathVariable("employeeId") Long employeeId, Model model){
        LocalDateTime now = LocalDateTime.now();
        LogbookEntry logbookEntry = new LogbookEntry("", now.minusHours(1), now);

        Employee empl = this.workLogFacade.findEmployeeById(employeeId);
        logbookEntry.setEmployee(empl);
        model.addAttribute("entry", logbookEntry);
        logger.debug("entry template {}", logbookEntry);
        return "entry";
    }

    @RequestMapping(value = "/albums/{albumId}/songs/new", method = RequestMethod.GET)
    public String initNewSong(@PathVariable("albumId") Long albumId,
                              @ModelAttribute("song") Song song,
                              Model model){
        Album album = this.workLogFacade.findAlbumById(albumId);
        model.addAttribute("album", album);
        return "song";
    }

    @RequestMapping(value = "/albums/{albumId}/songs/new/add", method = RequestMethod.POST)
    public String addNewSong(@PathVariable("albumId") Long albumId,
                             @ModelAttribute("song") Song song,
                             Model model,
                             BindingResult result){
        if(result.hasErrors()){
            return "entry";
        } else {
            Genre genre = new Genre("CLASSIC");
            Album album = workLogFacade.findAlbumById(albumId);
            SongDuration sd = new SongDuration(song.getDuration().getMinutes(), song.getDuration().getSeconds());
            Song s = new Song(song.getTitle(), sd, song.getInterpreter());
            genre.addSong(s);
            album.addSong(s);
            workLogFacade.syncAlbum(album);
            logger.debug("added/updated album {}", album);
            logger.debug("added/updated album {}", song);
            System.out.println("minutes: " + song.getDuration().getMinutes());
            System.out.println("seconds: " + song.getDuration().getSeconds());
            System.out.println("title: " + song.getGenre().getName());

        }
        return "redirect:/albums/{albumId}/songs";
    }

    /*Genre rock = new Genre("ROCK");

    Album a1 = new Album("Highway to Hell",1979);

    SongDuration sd1 = new SongDuration(3,32);
    Song s1 = new Song("Girls Got Rhythm", sd1, "ACDC");
    rock.addSong(s1);
    a1.addSong(s1);
    workLog.syncAlbum(a1);*/

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(LocalDateTime.class, new LocalDateTimeEditor(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @RequestMapping(value="/employees/{employeeId}/entries/new", method = RequestMethod.POST)
    public String processNew(@PathVariable("employeeId") Long employeeId,
                             @ModelAttribute("entry") LogbookEntry entry,
                             BindingResult result, Model model){
        return internalProcessUpdate(employeeId, entry, result);
    }



    private String internalProcessUpdate(Long employeeId, LogbookEntry entry, BindingResult result) {
        if(result.hasErrors()){
            return "entry";
        } else {
            entry.setEmployee(workLogFacade.findEmployeeById(employeeId));
            entry = workLogFacade.syncLogbookEntry(entry);

            logger.debug("added/updated entry {}", entry);
            return "redirect:/employees/{employeeId}/entries";
        }
    }

    @RequestMapping(value="/employees/{employeeId}/entries/{entryId}/update", method = RequestMethod.GET)
    public String initUpdate(@PathVariable("employeeId") Long employeeId,
                          @PathVariable("entryId") Long entryId,
                          Model model){

        LogbookEntry entry = workLogFacade.findLogbookEntryById(entryId);

        model.addAttribute("entry", entry);
        logger.debug("entry template {}", entry);
        return "entry";
    }

    @RequestMapping(value="/employees/{employeeId}/entries/{entryId}/update", method = RequestMethod.POST)
    public String processUpdate(@PathVariable("employeeId") Long employeeId,
                                @ModelAttribute("entry") LogbookEntry entry,
                                BindingResult result){
        //model.getAttr ... wenn nicht @ModelAttribut
        return internalProcessUpdate(employeeId, entry, result);
    }
}
