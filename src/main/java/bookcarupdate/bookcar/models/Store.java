package bookcarupdate.bookcar.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tblstore")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeID;
    private String storeName;
    @Column(nullable = false)
    private String phoneNumber;
    private String introduce;
    private Date createdAt;
    private Date updateAt;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @JsonBackReference  // Đánh dấu mối quan hệ không quản lý
    private User user;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Product> productList;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

}
