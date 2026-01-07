package bookcarupdate.bookcar.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tblorder")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Order {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderID;

    @ManyToOne
    @JoinColumn(name = "pick_location_id")
    private Location pickLocation;
    @ManyToOne
    @JoinColumn(name = "destination_location_id")
    private Location destinationLocation;

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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
