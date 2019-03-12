package geoquizz.mobile.bundary;

import geoquizz.mobile.entity.Photo;
import geoquizz.mobile.entity.Serie;
import org.springframework.data.repository.CrudRepository;

public interface PhotoResource extends CrudRepository<Photo, String> {
    //ecrire ici les methods perso (findBy...)
}