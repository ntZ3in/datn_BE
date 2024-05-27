package bookcarupdate.bookcar.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.w3c.dom.Text;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tblproduct")
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
    private int remain_seat;
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonManagedReference  // Đánh dấu mối quan hệ quản lý
    @JsonIgnore
    private List<Order> orderList;

}
