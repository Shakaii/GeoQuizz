package geoquizz.player.bundary;

import geoquizz.player.entity.Test;
import org.springframework.data.repository.CrudRepository;

public interface SerieResource extends CrudRepository<Test, String> {
    //ecrire ici les methods perso (findBy...)
}
