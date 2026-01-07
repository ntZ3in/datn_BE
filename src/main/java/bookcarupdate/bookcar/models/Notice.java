package bookcarupdate.bookcar.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tblnotice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeID;
    private String title;
    private String content;
    private Date createdAt;
    private String storeName;
    private String status;
    private Date lastUpdate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference  // Đánh dấu mối quan hệ không quản lý
    private Product product;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

}
