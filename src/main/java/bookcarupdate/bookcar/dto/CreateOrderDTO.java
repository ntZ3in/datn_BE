package bookcarupdate.bookcar.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CreateOrderDTO {
    private LocationDTO pickUpAddress;
    private LocationDTO destinationAddress;
    private LocalDateTime pickTime;
    private String message;
    private int quantity;
    private String phoneNumber;
    private double price;
    private double totalPrice;
    private String orderStatus;
    private Long id; // => id order
    private String emailUser;
}
