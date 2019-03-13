package geoquizz.mobile.bundary;

import geoquizz.mobile.entity.Photo;

import geoquizz.mobile.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/mobile", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRepresentation {

    private UserResource sr;

    public UserRepresentation(UserResource sr) {
        this.sr = sr;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> postRegister(@RequestBody User user) {

        if (sr.findByUsername(user.getUsername()).equals(null)){
            user.setId(UUID.randomUUID().toString());
            User saved = sr.save(user);
            HttpHeaders rH = new HttpHeaders();
            return new ResponseEntity<>(sr.findAll(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("This username is already taken", HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> postLogin(@RequestBody User user) {

        if (sr.findByUsername(user.getUsername()).getUsername() == user.getUsername()){
            User foundUser = sr.findByUsername(user.getUsername());
            HttpHeaders rH = new HttpHeaders();
            if (user.getPassword() == foundUser.getPassword()){
                user.setToken(UUID.randomUUID().toString());
                return new ResponseEntity<>(user.getToken(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>("bad password or login", HttpStatus.FORBIDDEN);
            }
        }
        else {
            return new ResponseEntity<>("bad password or login", HttpStatus.FORBIDDEN);
        }
    }





}