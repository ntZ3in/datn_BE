package bookcarupdate.bookcar.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CreateOrderDTO {
    private String pickUpAddress;
    private String destinationAddress;
    private LocalDateTime pickTime;
    private String message;
    private int quantity;
    private String phoneNumber;
    private double price;
    private double totalPrice;
    private String orderStatus;
    private Long id;
    private String emailUser;

}
