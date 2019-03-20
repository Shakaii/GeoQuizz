package geoquizz.backoffice.bundary;

import geoquizz.backoffice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class UserRepresentation {

    @Autowired
    UserResource ur;
    BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody() User u) {
        if (ur.existsByUsername(u.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            u.setPassword(pe.encode(u.getPassword()));
            u.setId(UUID.randomUUID().toString());
            ur.save(u);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
