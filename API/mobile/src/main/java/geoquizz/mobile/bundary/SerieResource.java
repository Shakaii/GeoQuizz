package geoquizz.mobile.bundary;

import geoquizz.mobile.entity.Serie;
import org.springframework.data.repository.CrudRepository;

public interface SerieResource extends CrudRepository<Serie, String> {
    //ecrire ici les methods perso (findBy...)
}