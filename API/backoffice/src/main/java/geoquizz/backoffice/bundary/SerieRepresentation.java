package geoquizz.backoffice.bundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import geoquizz.backoffice.entity.Photo;
import geoquizz.backoffice.entity.Serie;
import geoquizz.backoffice.exception.NotFound;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api( description="Gére les routes liées aux séries sur le back office.")
@RequestMapping(value="/office", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Serie.class)
public class SerieRepresentation {

    private final SerieResource sr;
    private final PhotoResource pr;

    public SerieRepresentation(SerieResource sr, PhotoResource pr){
        this.sr=sr;
        this.pr=pr;
    }
   
    @ApiOperation(value = "Récupère toutes les séries")
    @GetMapping(value = "/series")
    public ResponseEntity<?> getAllSeries(){
        Iterable<Serie> allSeries = sr.findAll();
        return new ResponseEntity<>(serie2Resource(allSeries),HttpStatus.OK);
    }

    private Resources<Resource<Serie>> serie2Resource(Iterable<Serie> series) {
        Link selfLink = linkTo(SerieRepresentation.class).withSelfRel();
        List<Resource<Serie>> serieResources = new ArrayList();
        series.forEach(serie
            -> serieResources.add(serieToResource(serie, false)));
        return new Resources<>(serieResources, selfLink);
    }

    private Resource<Serie> serieToResource(Serie serie, Boolean collection) {
        Link selfLink = linkTo(SerieRepresentation.class).slash("series")
            .slash(serie.getId())
            .withSelfRel();
        if(collection){
            Link collectionLink = linkTo(SerieRepresentation.class).withRel("collection");
            return new Resource<>(serie, selfLink, collectionLink);
        }else{
            return new Resource<>(serie, selfLink);
        }
    }

    @ApiOperation(value = "Récupère une série en particulier en spécifiant son id (404 si la série n'est pas trouvée)")
    @GetMapping(value = "/series/{serieId}")
    public ResponseEntity<?> getOne(@PathVariable("serieId") String id)
            throws NotFound {
        /*
        return Optional.ofNullable(sr.findById(id))
                .filter(Optional::isPresent)
                .map(serie -> new ResponseEntity<>(serieToResource(serie.get(),false), HttpStatus.OK))
                .orElseThrow(() -> new NotFound("Serie inexsitante"));
                */

        Optional o = sr.findById(id);
        if (o.isPresent()) {
            return new ResponseEntity<>(o.get(), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Crée l série envoyée dans le body et la renvoie")
    @PostMapping(value = "/series")
    public ResponseEntity<?> postMethod(@RequestBody Serie serie) {
        serie.setId(UUID.randomUUID().toString());
        Serie saved = sr.save(serie);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(linkTo(SerieRepresentation.class).slash("series").slash(saved.getId()).toUri());
        return new ResponseEntity<>(saved, responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Permet d'ajouter la photo (présente dans le body de la requête) à la série dont l'id est renseignée. Renvoie la photo")
    @PostMapping(value = "/series/{id}/photos")
    public ResponseEntity<?> ajoutPhotos(@PathVariable("id") String id,
                                         @RequestBody Photo photo) throws NotFound {
        return sr.findById(id)
                .map(serie -> {
                    photo.setId(UUID.randomUUID().toString());
                    photo.setSerie(serie);
                    pr.save(photo);
                    return new ResponseEntity<>(photo, HttpStatus.CREATED);
                }).orElseThrow ( () -> new NotFound("Serie inexistante"));
    }

    @ApiOperation(value = "Modifie la distance voulue (présente dans le body) de la série dont l'id est renseignée. Renvoie la distance")
    @PutMapping(value = "/series/{id}/params")
    public ResponseEntity<?> changeParamsSerie(@PathVariable("id") String id,
                                               @RequestBody int dist) throws NotFound {
        return sr.findById(id)
                .map(serie -> {
                    serie.setDist(dist);
                    sr.save(serie);
                    return new ResponseEntity<>(serie.getDist(), HttpStatus.CREATED);
                }).orElseThrow( () -> new NotFound("Serie inexistante"));
    }

}