package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.dto.ProductSearchDTO;
import bookcarupdate.bookcar.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p.* FROM tblproduct p WHERE store_id = :store_id"
            , nativeQuery = true)
    List<Product> findAllStoreProducts(Long store_id);

    @Query(value = ""
            + "SELECT \n"
            + "    p.product_id,\n"
            + "    p.create_at,\n"
            + "    p.description,\n"
            + "    p.end_location_id,\n"
            + "    COALESCE(MIN(t.end_time), p.end_time) AS end_time,\n"
            + "    p.license_plates,\n"
            + "    p.name,\n"
            + "    p.phone_number,\n"
            + "    p.phone_number2,\n"
            + "    p.policy,\n"
            + "    COALESCE(MIN(t.price), p.price) AS price,\n"
            + "    p.start_location_id,\n"
            + "    COALESCE(MIN(t.start_time), p.start_time) AS start_time,\n"
            + "    p.update_at,\n"
            + "    p.type,\n"
            + "    p.utilities,\n"
            + "    p.store_id,\n"
            + "    p.status,\n"
            + "    p.owner_name,\n"
            + "    p.quantity_seat,\n"
            + "    p.quantity_seat - COALESCE(SUM(o.quantity), 0) AS remain_seat\n"
            + "FROM tblproduct p\n"
            + "LEFT JOIN tbltrip t ON p.product_id = t.product_id\n"
            + "LEFT JOIN tblorder o ON o.trip_id = t.trip_id\n"
            + "WHERE p.owner_name LIKE CONCAT('%', :key, '%')\n"
            + "AND ( p.status ='Hiện' )\n"
//            + "  AND (t.start_time >= CURTIME() OR t.start_time IS NULL)\n"
//            + "  AND (t.travel_date = CURDATE() OR t.travel_date IS NULL)\n"
            + "GROUP BY \n"
            + "    p.product_id, p.create_at, p.description,\n"
            + "    p.license_plates, p.name, p.phone_number, p.phone_number2,\n"
            + "    p.policy, p.end_location_id, p.end_location_id, p.update_at, p.type,\n"
            + "    p.utilities, p.store_id, p.status, p.owner_name, p.quantity_seat\n"
            + "ORDER BY p.owner_name DESC, start_time DESC, price ASC",
            nativeQuery = true)
    List<Product> findByKeyWord(@Param("key") String keyword);


    @Query(value = "SELECT  " +
            "    p.product_id,  " +
            "    p.create_at,  " +
            "    p.description,  " +
            "    p.end_location_id,  " +
            "    COALESCE(MIN(t.end_time), p.end_time) AS end_time,  " +
            "    MIN(t.travel_date) AS travel_date ,  " +
            "    p.license_plates,  " +
            "    p.name,  " +
            "    p.phone_number,  " +
            "    p.phone_number2,  " +
            "    p.policy,  " +
            "    COALESCE(MIN(t.price), p.price) AS price,  " +
            "    p.start_location_id,  " +
            "    COALESCE(MIN(t.start_time), p.start_time) AS start_time,  " +
            "    p.update_at,  " +
            "    p.type,  " +
            "    p.utilities,  " +
            "    p.store_id,  " +
            "    p.status,  " +
            "    p.owner_name,  " +
            "    p.quantity_seat,  " +
            "    TRIM(SUBSTRING_INDEX(p.name, '-', 1)) AS start_city,  " +
            "    TRIM(SUBSTRING_INDEX(p.name, '-', -1)) AS end_city,  " +
            "    p.quantity_seat - COALESCE(SUM(o.quantity), 0) AS remain_seat  " +
            "FROM tblproduct p  " +
            "LEFT JOIN tbltrip t ON p.product_id = t.product_id  " +
            "LEFT JOIN tblorder o ON o.trip_id = t.trip_id  " +
            "WHERE  " +
//            "    ( :date IS NULL OR t.travel_date = :date OR t.travel_date IS NULL )  " +
            "    ( :start_city IS NULL OR TRIM(SUBSTRING_INDEX(p.name, '-', 1)) LIKE CONCAT('%', :start_city, '%') )  " +
            "    AND ( :end_city IS NULL OR TRIM(SUBSTRING_INDEX(p.name, '-', -1)) LIKE CONCAT('%', :end_city, '%') )  " +
            "    AND ( p.status ='Hiện' )  " +
//            "    AND ( :start_address IS NULL OR p.start_address LIKE CONCAT('%', :start_address, '%') )  " +
//            "    AND ( :end_address IS NULL OR p.end_address LIKE CONCAT('%', :end_address, '%') )  " +
            "GROUP BY  " +
            "    p.product_id, p.create_at, p.description,  " +
            "    p.license_plates, p.name, p.phone_number, p.phone_number2,  " +
            "    p.policy, p.update_at, p.type,  " +
            "    p.utilities, p.store_id, p.status, p.owner_name, p.quantity_seat,  " +
            "    start_city, end_city , p.start_location_id, p.end_location_id " +
            "ORDER BY start_time DESC, price ASC",
            nativeQuery = true)
    List<Product> findByManyKeyWord(
            @Param("start_city") String startCity,
            @Param("end_city") String endCity);
//            @Param("start_time") LocalTime startTime,
//            @Param("date") LocalDate date,
//            @Param("start_address") String startAddress,
//            @Param("end_address") String endAddress);

}
