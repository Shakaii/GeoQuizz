package geoquizz.mobile.bundary;

import geoquizz.mobile.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserResource extends CrudRepository<User, String> {
    //ecrire ici les methods perso (findBy...)
}