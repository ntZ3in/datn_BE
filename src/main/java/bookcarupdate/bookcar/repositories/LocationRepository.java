package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository  extends JpaRepository<Location, Long> {
    @Query(value = "SELECT * FROM tbllocation \n" +
            "WHERE ABS(lat - ?) < 0.00001 AND ABS(lng - ?) < 0.00001\n", nativeQuery = true)
    Optional<Location> findByLatAndLng(double lat, double lng);
}
