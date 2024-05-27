package bookcarupdate.bookcar.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "tblorder")
public class Order {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderID;
    private String pickUpAddress;
    private String destinationAddress;
    private LocalDateTime pickTime;
    private String message;
    private int quantity;
    private String phoneNumber;
    private double price;
    private double totalPrice;
    private String orderStatus;
    private Date lastUpdate;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference  // Đánh dấu mối quan hệ không quản lý
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
