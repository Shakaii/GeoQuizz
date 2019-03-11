package geoquizz.backoffice.bundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestRepresentation {

    private TestResource tr;

    public TestRepresentation(TestResource tr) {
        this.tr = tr;
    }

    @GetMapping
    public ResponseEntity<?> getTest() {
        return new ResponseEntity<>(tr.findAll(), HttpStatus.OK);
    }
}
