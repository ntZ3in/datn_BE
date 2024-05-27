package bookcarupdate.bookcar.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
public class ProductDTO {
    private String license_plates;
    private String description;
    private String phone_number;
    private String phone_number2;
    private String start_address;
    private String end_address;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime start_time;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime end_time;
    private Double price;
    private String name;
    private int remain_seat;
    private String policy;
    private String utilities;
    private String type;
    private Date createAt;
    private Date updateAt;
    private List<ImageDTO> images;
    private String emailUser;
    private String status;
}
