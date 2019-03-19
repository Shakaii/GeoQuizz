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

import java.util.Random;
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
    public ResponseEntity<?> getGame(@PathVariable("id") String id, @RequestParam(value = "token", required = true) String token) {
        Partie p = pr.findById(id).get();
        if (p.getToken().equals(token)){
            p.setStatus(1);
            pr.save(p);
            return new ResponseEntity<>(pr.findById(id), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(value = "/score")
    public ResponseEntity<?> getScore() {
        return new ResponseEntity<>(pr.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<?> postGame(@RequestBody Partie p, @RequestParam(value = "serie", required = true) String id) {
        if (sr.findById(id).isPresent()) {
            Serie s = sr.findById(id).get();
            p.setId(UUID.randomUUID().toString());
            p.setToken(UUID.randomUUID().toString());
            p.setSerie(s);

            //we link photos and the game
            for (Photo po : s.getPhotos()) {
                po.addParties(p);
                //p.setPhotos(s.getPhotos());
                //poR.save(po);
            }
            
            p.setPhotos(s.getPhotos());
            //p.setPhotos();
            p.setVille(s.getVille());
            Partie saved = pr.save(p);
            HttpHeaders rH = new HttpHeaders();
            rH.setLocation(linkTo(PartieRepresentation.class).slash(saved.getId()).toUri());
            return new ResponseEntity<>(saved, rH, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/result/{id}")
    public ResponseEntity<?> postResult(@PathVariable("id") String id, @RequestBody Partie p) {
        Partie partie = pr.findById(id).get();
        if (partie.getToken().equals(p.getToken())){
            partie.setScore(p.getScore());
            partie.setSaveScore(p.getSaveScore());
            partie.setStatus(p.getStatus());
            pr.save(partie);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
