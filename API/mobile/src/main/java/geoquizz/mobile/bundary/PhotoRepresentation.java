package geoquizz.mobile.bundary;

import geoquizz.mobile.entity.Serie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/mobile", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhotoRepresentation {

    private SerieResource sr;

    public PhotoRepresentation(SerieResource sr) {
        this.sr = sr;
    }
}