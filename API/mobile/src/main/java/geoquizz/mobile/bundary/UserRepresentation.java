package geoquizz.mobile.bundary;

import geoquizz.mobile.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@Api( description="Gére les routes liées aux users sur le mobile.")
@RequestMapping(value = "/mobile", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRepresentation {

    private UserResource sr;

    public UserRepresentation(UserResource sr) {
        this.sr = sr;
    }

    @ApiOperation(value = "Permet d'enrgistrer l'utilisateur (présent dans le body de la requête). Renvoie l'utilisateur créé.")
    @PostMapping(value = "/register")
    public ResponseEntity<?> postRegister(@RequestBody User user) {

        if (sr.findByUsername(user.getUsername()).equals(null)){
            user.setId(UUID.randomUUID().toString());
            User saved = sr.save(user);
            HttpHeaders rH = new HttpHeaders();
            return new ResponseEntity<>(saved,rH, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("This username is already taken", HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Permet de connecter l'utilisateur (présent dans le body de la requête). Renvoie le token de l'utilisateur.")
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