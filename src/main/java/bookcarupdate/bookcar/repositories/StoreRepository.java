package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(
            value = """
        SELECT s.*
        FROM tblstore s
        JOIN tbluser u ON s.user_id = u.user_id
        WHERE u.email = :email
    """,
            nativeQuery = true
    )
    Store getStoreByOwnerEmail(@Param("email") String email);

}
