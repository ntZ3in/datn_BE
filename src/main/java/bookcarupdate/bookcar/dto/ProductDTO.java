package bookcarupdate.bookcar.dto;

import bookcarupdate.bookcar.models.Notice;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private String phone_number;
    private String phone_number2;
    private String license_plates;
    private LocalTime start_time;
    private LocalTime end_time;
    private Double price;
    private Integer quantity_seat;
    private String type;
    private String utilities;
    private String policy;
    private String status;
    private String emailUser;

    private LocationDTO startLocation;
    private LocationDTO endLocation;

    private List<ImageDTO> images;
    private List<CreateNoticeDTO> notices;
    private List<StopDTO> stopDTOS;
}

