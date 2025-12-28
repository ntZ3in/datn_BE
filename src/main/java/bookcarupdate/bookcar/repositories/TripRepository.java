package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.models.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query(value = "select * from tbltrip t where t.product_id =:productId and t.travel_date =:travelDate", nativeQuery = true)
    Optional<Trip> findByProductProductIDAndTravelDate(@Param("productId") Long productId, @Param("travelDate") LocalDate travelDate);

    List<Trip> findByTravelDateAndStartTimeAfterAndStatus(
            LocalDate travelDate,
            LocalTime startTime,
            String status
    );

    Page
            <Trip> findByTravelDateAndStartTimeAfterAndStatus(
            LocalDate travelDate,
            LocalTime startTime,
            String status,
            Pageable pageable
    );

}
