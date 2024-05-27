package bookcarupdate.bookcar.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
public class SearchFullDTO {
    private String start_address;
    private String end_address;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;

}
