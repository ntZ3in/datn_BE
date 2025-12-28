package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.models.Location;
import bookcarupdate.bookcar.repositories.LocationRepository;
import bookcarupdate.bookcar.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public Location findOrCreate(String name, double lat, double lng) {
        Optional<Location> existing = locationRepository
                .findByLatAndLng(lat, lng);

        if (existing.isPresent()) return existing.get();

        Location newLocation = new Location();
        newLocation.setName(name);
        newLocation.setLat(lat);
        newLocation.setLng(lng);
        return locationRepository.save(newLocation);
    }

}
