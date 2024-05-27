package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Modifying
    @Query(value = "DELETE FROM tblimage WHERE tblproduct_id =:id", nativeQuery = true )
    void deleteAllByIdPro(@Param("id") Long id);
}
