package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.models.Trip;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface TripService {
    public Optional<Trip> getOrCreateTrip(Long productId, LocalDate travelDate, LocalTime startTime);
}
