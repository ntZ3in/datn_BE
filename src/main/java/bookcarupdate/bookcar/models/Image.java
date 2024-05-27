package bookcarupdate.bookcar.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblimage")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "tblproduct_id")
    @JsonBackReference  // Đánh dấu mối quan hệ không quản lý
    private Product product;
}
