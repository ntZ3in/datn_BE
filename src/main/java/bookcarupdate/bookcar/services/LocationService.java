package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.models.Location;

public interface LocationService {
    public Location findOrCreate(String name, double lat, double lng);
}
