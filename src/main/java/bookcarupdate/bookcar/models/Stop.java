package bookcarupdate.bookcar.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tblstop")
public class Stop {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stop_id")
    private Long stopID;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime stopTime;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private String type;
    private boolean deleted;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference  // Đánh dấu mối quan hệ không quản lý
    private Product product;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

//    public boolean getDeleted() {
//        return this.deleted;
//    }
}
