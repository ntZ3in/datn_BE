package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.Trip;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.repositories.TripRepository;
import bookcarupdate.bookcar.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final ProductRepository productRepository;

    @Override
    public Optional<Trip> getOrCreateTrip(Long productId, LocalDate travelDate, LocalTime startTime) {
        Optional<Trip> existing = tripRepository.findByProductProductIDAndTravelDate(productId, travelDate); // duy nhat 1 sản phẩm - 1 ngày
        System.out.println("productid: "+ productId+", travelDate: " + travelDate +" startTime: " + startTime);

        if (existing.isPresent()) {
            Trip trip = existing.get();
            // chỉ trả về nếu start_time >= tham số
            if (!trip.getStartTime().isBefore(startTime) && trip.getStatus().equalsIgnoreCase("TRUE")) {
                return Optional.of(trip);
            }else {
                return Optional.empty();
            }
        }

        // Nếu chưa co trip của ngày đó
        // Lấy Product (tuyến cố định)
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getStart_time().isBefore(startTime)) {
            return Optional.empty();
        }

        // Tạo Trip từ Product (template)
        Trip trip = new Trip();
        trip.setProduct(product);
        trip.setTravelDate(travelDate);
        trip.setStartTime(product.getStart_time());
        trip.setEndTime(product.getEnd_time());
        trip.setPrice(product.getPrice());
        trip.setRemainSeat(product.getQuantity_seat());
        trip.setStatus("TRUE");

        tripRepository.save(trip);
        return Optional.of(trip);
    }
}
