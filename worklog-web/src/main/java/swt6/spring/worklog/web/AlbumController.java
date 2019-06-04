package swt6.spring.worklog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.logic.WorkLogFacade;

import java.util.List;

@Controller
public class AlbumController {

    private Logger logger = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    private WorkLogFacade workLogFacade;

    @RequestMapping("albums")
    public String list(Model model){
        List<Album> albums = this.workLogFacade.findAllAlbums();
        model.addAttribute("albums",albums);
        logger.debug("albums: " + albums);
        return "albumList";
    }

    @RequestMapping("albums/new")
    public String newAlbum(@ModelAttribute("album") Album album,
                           BindingResult result, Model model){

        return "album";
    }

    @RequestMapping(value="albums/new", method = RequestMethod.POST)
    public String addAlbum(@ModelAttribute("album") Album album,
                           BindingResult result, Model model){

        return internalProcessAddAlbum(album, result);
    }

    private String internalProcessAddAlbum(Album album, BindingResult result) {
        if(result.hasErrors()){
            return "album";
        } else {
            workLogFacade.syncAlbum(album);
            logger.debug("added/updated album {}", album);
            return "redirect:/albums";
        }
    }
}
