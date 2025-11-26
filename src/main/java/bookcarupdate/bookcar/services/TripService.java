package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.models.Trip;

import java.time.LocalDate;

public interface TripService {
    public Trip getOrCreateTrip(Long productId, LocalDate travelDate);
}
