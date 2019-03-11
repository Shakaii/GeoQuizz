package geoquizz.mobile.bundary;

import geoquizz.mobile.entity.Test;
import org.springframework.data.repository.CrudRepository;

public interface TestResource extends CrudRepository<Test, String> {
    //ecrire ici les methods perso (findBy...)
}
