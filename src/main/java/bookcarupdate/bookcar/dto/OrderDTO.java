package bookcarupdate.bookcar.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

public interface OrderDTO {
    Long order_id() ;
    String pick_up_address();
    String destination_address();
    LocalDateTime pick_time();
    String message();
    int quantity();
    String phone_number();
    double price();
    double total_price();
    String order_status();
    Date created_at();
    String name();
    String owner_name();
}
