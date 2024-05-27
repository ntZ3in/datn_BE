package bookcarupdate.bookcar.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;

@Data
public class TestDTO {
    private String abc;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;
    private List<ImageDTO> images;
}
