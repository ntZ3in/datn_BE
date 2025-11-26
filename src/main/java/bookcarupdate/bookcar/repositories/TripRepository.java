package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findByProductProductIDAndTravelDate(Long productId, LocalDate travelDate);
}
