package bookcarupdate.bookcar.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SearchRequest {
        private String key;
        private String from_city;
        private String to_city;
        private LocalTime start_time;
        private LocalDate date;

        private LocationDTO start_address;
        private LocationDTO end_address;
        private LocationDTO userLocation;
}
