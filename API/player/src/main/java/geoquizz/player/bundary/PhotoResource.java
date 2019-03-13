package geoquizz.player.bundary;

import geoquizz.player.entity.Photo;
import geoquizz.player.entity.Serie;
import org.springframework.data.repository.CrudRepository;

public interface PhotoResource extends CrudRepository<Photo, String> {
    //ecrire ici les methods perso (findBy...)
}
