package geoquizz.player.bundary;

import geoquizz.player.entity.Partie;
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
@RequestMapping(value = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartieRepresentation {

    private PartieResource pr;

    @Autowired SerieResource sr;
    @Autowired PhotoResource poR;

    public PartieRepresentation(PartieResource pr) {
        this.pr = pr;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getGame(@PathVariable("id") String id) {
        Partie p = pr.findById(id).get();
        p.setStatus(1);
        pr.save(p);
        return new ResponseEntity<>(pr.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<?> postGame(@RequestBody Partie p, @RequestParam(value = "serie", required = true) String id) {
        if (sr.findById(id).isPresent()) {
            Serie s = sr.findById(id).get();
            p.setId(UUID.randomUUID().toString());
            p.setSerie(s);
            for (Photo po : s.getPhotos()) {
                po.addParties(p);
                //p.setPhotos(s.getPhotos());
                //poR.save(po);
            }
            p.setPhotos(s.getPhotos());
            //p.setPhotos();
            Partie saved = pr.save(p);
            HttpHeaders rH = new HttpHeaders();
            rH.setLocation(linkTo(PartieRepresentation.class).slash(saved.getId()).toUri());
            return new ResponseEntity<>(saved, rH, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(value = "/result")
    public ResponseEntity<?> putGame(@RequestBody Partie p) {
        pr.save(p);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
