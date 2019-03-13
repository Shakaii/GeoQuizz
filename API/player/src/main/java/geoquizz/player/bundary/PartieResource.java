package geoquizz.player.bundary;

import geoquizz.player.entity.Partie;
import org.springframework.data.repository.CrudRepository;

public interface PartieResource extends CrudRepository<Partie, String> {
    //ecrire ici les methods perso (findBy...)
}
