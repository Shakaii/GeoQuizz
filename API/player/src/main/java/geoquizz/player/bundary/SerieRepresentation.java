package geoquizz.player.bundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/player", produces = MediaType.APPLICATION_JSON_VALUE)
public class SerieRepresentation {

    private SerieResource sr;

    public SerieRepresentation(SerieResource sr) {
        this.sr = sr;
    }

    @GetMapping(value = "/serie")
    public ResponseEntity<?> getTest() {
        return new ResponseEntity<>(sr.findAll(), HttpStatus.OK);
    }
}
