package geoquizz.player.bundary;

import geoquizz.player.entity.Photo;
import geoquizz.player.entity.Serie;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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

    @ApiOperation(value = "Récupère toutes les séries")
    @GetMapping(value = "/series")
    public ResponseEntity<?> getSeries() {
        return new ResponseEntity<>(sr.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Récupère une série")
    @GetMapping(value = "/serie/{id}")
    public ResponseEntity<?> getSerie(@PathVariable("id") String id) {
        return new ResponseEntity<>(sr.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Crée l série envoyée dans le body et la renvoie")
    @PostMapping(value = "/serie")
    public ResponseEntity<?> postSerie(@RequestBody Serie s) {
        s.setId(UUID.randomUUID().toString());
        Serie saved = sr.save(s);
        HttpHeaders rH = new HttpHeaders();
        rH.setLocation(linkTo(SerieRepresentation.class).slash(saved.getId()).toUri());
        return new ResponseEntity<>(saved, rH, HttpStatus.CREATED);
    }

    @ApiOperation(value = "(Sert uniquement si l'autre api est éteinte) Permet d'ajouter la photo (présente dans le body de la requête) à la série dont l'id est renseignée.")
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
