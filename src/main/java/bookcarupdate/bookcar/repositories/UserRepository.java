package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.models.Role;
import bookcarupdate.bookcar.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
