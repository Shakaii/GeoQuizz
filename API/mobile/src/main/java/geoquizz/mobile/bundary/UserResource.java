package geoquizz.mobile.bundary;

import geoquizz.mobile.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserResource extends CrudRepository<User, String> {
    User findByUsername(String username);
    User findByToken(String token);
}