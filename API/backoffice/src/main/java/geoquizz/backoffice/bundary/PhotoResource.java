package geoquizz.backoffice.bundary;

import org.springframework.data.repository.CrudRepository;

import geoquizz.backoffice.entity.Photo;

public interface PhotoResource extends CrudRepository<Photo, String> {

    //methodes

}