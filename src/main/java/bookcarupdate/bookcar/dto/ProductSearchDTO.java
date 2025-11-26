package bookcarupdate.bookcar.dto;

import bookcarupdate.bookcar.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDTO {
    private Long productID;
    private String license_plates;
    private String description;
    private String phone_number;
    private String phone_number2;
    private String start_address;
    private String end_address;
    private LocalTime start_time;
    private LocalTime end_time;
    private Double price;
    private String name;
    private int quantity_seat;
    private String policy;
    private String utilities;
    private String type;
    private Date createAt;
    private Date updateAt;
    private String status;
    private String owner_name;
    private Long store_id;
//    private List<ImageDTO> imageDTOS;
//    private List<CreateNoticeDTO> noticeDTOS;

    private Long trip_id;
    private LocalDate travel_date;
    private LocalTime trip_start_time;
    private LocalTime trip_end_time;
    private Double trip_price;
    private int remain_seat;
    private String trip_status;

}
