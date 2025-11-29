package bookcarupdate.bookcar.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.Text;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblproduct")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonManagedReference
    private List<Image> images;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @JsonBackReference  // Đánh dấu mối quan hệ không quản lý
    private Store store;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonManagedReference  // Đánh dấu mối quan hệ quản lý
    private List<Stop> stopList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonManagedReference  // Đánh dấu mối quan hệ quản lý
    private List<Notice> noticeList;

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", license_plates='" + license_plates + '\'' +
                ", description='" + description + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", phone_number2='" + phone_number2 + '\'' +
                ", start_address='" + start_address + '\'' +
                ", end_address='" + end_address + '\'' +
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
                ", images=" + images +
                ", store=" + store +
                ", stopList=" + stopList +
                ", noticeList=" + noticeList +
                '}';
    }

    //    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    @JsonIdentityReference(alwaysAsId = true)
//    @JsonManagedReference  // Đánh dấu mối quan hệ quản lý
//    @JsonIgnore
//    private List<Order> orderList;

}
