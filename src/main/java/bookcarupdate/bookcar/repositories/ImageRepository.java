package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.dto.ImageDTO;
import bookcarupdate.bookcar.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Modifying
    @Query(value = "DELETE FROM tblimage WHERE tblproduct_id =:id", nativeQuery = true )
    void deleteAllByIdPro(@Param("id") Long id);

    @Query("select new bookcarupdate.bookcar.dto.ImageDTO(i.image_url) from Image i left join Product p on p.productID = i.product.productID where p.productID=:id")
    Optional<List<ImageDTO>> findAllByProductId(@Param("id") Long tblproduct_id);
}
