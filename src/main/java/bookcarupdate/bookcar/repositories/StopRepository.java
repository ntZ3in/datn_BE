package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.models.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

}
