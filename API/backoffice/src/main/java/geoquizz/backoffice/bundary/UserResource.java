package geoquizz.backoffice.bundary;

import geoquizz.backoffice.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserResource extends CrudRepository<User, String> {
    boolean existsByUsername(String username);

    User findByUsername(String username);
}
