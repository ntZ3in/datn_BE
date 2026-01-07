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
    private LocationDTO startLocation;
    private LocationDTO endLocation;
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
    private List<ImageDTO> imageDTOS;
    private List<CreateNoticeDTO> noticeDTOS;
    private List<StopDTO> stopDTOS;

    private Long trip_id;
    private LocalDate travel_date;
    private LocalTime trip_start_time;
    private LocalTime trip_end_time;
    private Double trip_price;
    private int remain_seat;
    private String trip_status;

    private Double distance;

    @Override
    public String toString() {
        return "ProductSearchDTO{" +
                "productID=" + productID +
                ", license_plates='" + license_plates + '\'' +
                ", description='" + description + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", phone_number2='" + phone_number2 + '\'' +
                ", startLocation=" + startLocation +
                ", endLocation=" + endLocation +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", quantity_seat=" + quantity_seat +
                ", policy='" + policy + '\'' +
                ", utilities='" + utilities + '\'' +
                ", type='" + type + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", status='" + status + '\'' +
                ", owner_name='" + owner_name + '\'' +
                ", store_id=" + store_id +
                ", imageDTOS=" + imageDTOS +
                ", noticeDTOS=" + noticeDTOS +
                ", stopDTOS=" + stopDTOS +
                ", trip_id=" + trip_id +
                ", travel_date=" + travel_date +
                ", trip_start_time=" + trip_start_time +
                ", trip_end_time=" + trip_end_time +
                ", trip_price=" + trip_price +
                ", remain_seat=" + remain_seat +
                ", trip_status='" + trip_status + '\'' +
                ", distance=" + distance +
                '}';
    }
}
