package bookcarupdate.bookcar.dto;

import bookcarupdate.bookcar.models.Location;
import lombok.*;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@ToString
@Getter
@Setter
public class StopDTO {
    private LocationDTO location;
    private LocalTime stop_time;
    private String type;
    private boolean deleted;
}
