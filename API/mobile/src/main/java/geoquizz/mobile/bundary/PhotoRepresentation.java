package geoquizz.mobile.bundary;

import geoquizz.mobile.entity.Photo;
import geoquizz.mobile.entity.Serie;
import geoquizz.mobile.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserResource ur;

    @Autowired
    private PhotoResource pr;

    public PhotoRepresentation(SerieResource sr) {
        this.sr = sr;
    }

    public void UserRepresentation(UserResource ur) {
        this.ur = ur;
    }

    @PostMapping(value = "/photos")
    public ResponseEntity<?> postPhotos(@RequestBody Photo[] photos, String token) {
        for (Photo photo : photos) {
            photo.setId(UUID.randomUUID().toString());
            User user = ur.findByToken(token);
            photo.setUser(user);
            pr.save(photo);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}