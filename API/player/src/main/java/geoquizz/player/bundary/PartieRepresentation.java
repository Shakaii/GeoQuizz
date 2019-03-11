package geoquizz.player.bundary;

import geoquizz.player.entity.Partie;
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
@RequestMapping(value = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartieRepresentation {

    private PartieResource pr;

    @Autowired SerieResource sr;

    public PartieRepresentation(PartieResource pr) {
        this.pr = pr;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getGame(@PathVariable("id") String id) {
        pr.findById(id).get().setStatus(1);
        return new ResponseEntity<>(pr.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/photos")
    public ResponseEntity<?> getPhotos(@PathVariable("id") String id) {
        return new ResponseEntity<>(pr.findById(id).get().getPhotos(), HttpStatus.OK);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<?> postSerie(@RequestBody Partie p, @RequestParam(value = "serie", required = true) String id) {
        if (sr.findById(id).isPresent()) {
            Serie s = sr.findById(id).get();
            p.setId(UUID.randomUUID().toString());
            p.setSerie(s);
            Partie saved = pr.save(p);
            HttpHeaders rH = new HttpHeaders();
            rH.setLocation(linkTo(PartieRepresentation.class).slash(saved.getId()).toUri());
            return new ResponseEntity<>(saved, rH, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
