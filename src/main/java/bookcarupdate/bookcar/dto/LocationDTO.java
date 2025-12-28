package bookcarupdate.bookcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDTO {
    private String name;
    private Double lat;
    private Double lng;
}
