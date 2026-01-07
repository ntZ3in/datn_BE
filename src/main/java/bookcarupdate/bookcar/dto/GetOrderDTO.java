package bookcarupdate.bookcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class GetOrderDTO {
    private Long orderId;
    private String name; // tên vé
    private String owner_name; // tên nhà xe
    private LocationDTO pickUpAddress;
    private LocationDTO destinationAddress;
    private LocalDateTime pickTime;
    private String message;
    private int quantity;
    private String phoneNumber;
    private double price;
    private double totalPrice;
    private String orderStatus;
    private Long tripId; // => đặt sp nào, ngày nào
    private Date createdAt;
//    private String emailUser;

}
