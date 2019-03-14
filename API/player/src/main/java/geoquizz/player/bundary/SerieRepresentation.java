package geoquizz.player.bundary;

import geoquizz.player.entity.Photo;
import geoquizz.player.entity.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@Api( description="Gére les routes liées aux séries sur le côté player.")
@RequestMapping(value = "/player", produces = MediaType.APPLICATION_JSON_VALUE)
public class SerieRepresentation {

    private SerieResource sr;

    @Autowired
    private PhotoResource pr;

    public SerieRepresentation(SerieResource sr) {
        this.sr = sr;
    }

    @GetMapping(value = "/serie")
    public ResponseEntity<?> getTest() {
        return new ResponseEntity<>(sr.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/serie")
    public ResponseEntity<?> postSerie(@RequestBody Serie s) {
        s.setId(UUID.randomUUID().toString());
        Serie saved = sr.save(s);
        HttpHeaders rH = new HttpHeaders();
        rH.setLocation(linkTo(SerieRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(saved, rH, HttpStatus.CREATED);
    }

    @PostMapping(value = "/serie/{id}/photos")
    public ResponseEntity<?> postPhotos(@RequestBody Photo[] photos, @PathVariable("id") String id) {
        Serie s = sr.findById(id).get();
        for (Photo photo : photos) {
            photo.setId(UUID.randomUUID().toString());
            photo.setSerie(s);
            pr.save(photo);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
