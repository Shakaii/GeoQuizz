package geoquizz.backoffice.bundary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import geoquizz.backoffice.entity.Serie;

@RestController
@RequestMapping(value="/series", produces=MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Serie.class)
public class SerieRepresentation {

    private final SerieResource sr;
    private final PhotoResource pr;

    public SerieRepresentation(SerieResource sr, PhotoResource pr){
        this.sr=sr;
        this.pr=pr;
    }

    @GetMapping
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
        Link selfLink = linkTo(SerieRepresentation.class)
            .slash(serie.getId())
            .withSelfRel();
        if(collection){
            Link collectionLink = linkTo(SerieRepresentation.class).withRel("collection");
            return new Resource<>(serie, selfLink, collectionLink);
        }else{
            return new Resource<>(serie, selfLink);
        }
    }

}