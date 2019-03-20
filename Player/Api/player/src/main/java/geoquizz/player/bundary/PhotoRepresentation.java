package geoquizz.player.bundary;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;

@RestController
@Api( description="Gére les routes liées aux photos sur le côté player.")
@RequestMapping(value = "/player", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhotoRepresentation {

    private SerieResource sr;

    public PhotoRepresentation(SerieResource sr) {
        this.sr = sr;
    }
}
