package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.Trip;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.repositories.TripRepository;
import bookcarupdate.bookcar.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final ProductRepository productRepository;

    @Override
    public Trip getOrCreateTrip(Long productId, LocalDate travelDate) {
        Optional<Trip> existing = tripRepository.findByProductProductIDAndTravelDate(productId, travelDate);

        if (existing.isPresent()) {
            return existing.get();
        }

        // Lấy Product (tuyến cố định)
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Tạo Trip từ Product (template)
        Trip trip = new Trip();
        trip.setProduct(product);
        trip.setTravelDate(travelDate);
        trip.setStartTime(product.getStart_time());
        trip.setEndTime(product.getEnd_time());
        trip.setPrice(product.getPrice());
        trip.setRemainSeat(product.getQuantity_seat());
        trip.setStatus("ACTIVE");

        return tripRepository.save(trip);
    }
}
