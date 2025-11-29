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

    @Query("SELECT new bookcarupdate.bookcar.dto.ProductSearchDTO(" +
            "p.productID, p.license_plates, p.description, p.phone_number, p.phone_number2, " +
            "p.start_address, p.end_address, p.start_time, p.end_time, p.price, p.name, " +
            "p.quantity_seat, p.policy, p.utilities, p.type, p.createAt, p.updateAt, " +
            "p.status, p.owner_name, p.store.storeID, " +
            "t.tripId, t.travelDate, t.startTime, t.endTime, t.price, t.remainSeat, t.status) " +
            "FROM Product p LEFT JOIN Trip t ON p.productID = t.product.productID " +
            "WHERE t.travelDate = CURRENT_DATE AND t.startTime >= CURRENT_TIME AND t.status = 'TRUE'" +
            "ORDER BY t.startTime DESC, t.price ASC")
    List<ProductSearchDTO> findAll2();

    @Query("SELECT new bookcarupdate.bookcar.dto.ProductSearchDTO(" +
            "p.productID, p.license_plates, p.description, p.phone_number, p.phone_number2, " +
            "p.start_address, p.end_address, p.start_time, p.end_time, p.price, p.name, " +
            "p.quantity_seat, p.policy, p.utilities, p.type, p.createAt, p.updateAt, " +
            "p.status, p.owner_name, p.store.storeID, " +
            "t.tripId, t.travelDate, t.startTime, t.endTime, t.price, t.remainSeat, t.status) " +
            "FROM Product p LEFT JOIN Trip t ON p.productID = t.product.productID " +
            "WHERE t.travelDate = CURRENT_DATE AND t.startTime >= CURRENT_TIME AND t.status = 'TRUE' " +
            "ORDER BY t.startTime DESC, t.price ASC")
    Page<ProductSearchDTO> findAllPagi(Pageable pageable);

    @Query(value = "SELECT p.* FROM product_tb p WHERE display = true and deleted = false", nativeQuery = true)
    List<Product> findAllActiveProducts();

    @Query(value = "SELECT p.* FROM tblproduct p WHERE store_id = :store_id"
            , nativeQuery = true)
    List<Product> findAllStoreProducts(Long store_id);

    @Query(value = ""
            + "SELECT \n"
            + "    p.product_id,\n"
            + "    p.create_at,\n"
            + "    p.description,\n"
            + "    p.end_address,\n"
            + "    COALESCE(MIN(t.end_time), p.end_time) AS end_time,\n"
            + "    p.license_plates,\n"
            + "    p.name,\n"
            + "    p.phone_number,\n"
            + "    p.phone_number2,\n"
            + "    p.policy,\n"
            + "    COALESCE(MIN(t.price), p.price) AS price,\n"
            + "    p.start_address,\n"
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
//            + "  AND (t.start_time >= CURTIME() OR t.start_time IS NULL)\n"
//            + "  AND (t.travel_date = CURDATE() OR t.travel_date IS NULL)\n"
            + "GROUP BY \n"
            + "    p.product_id, p.create_at, p.description, p.end_address,\n"
            + "    p.license_plates, p.name, p.phone_number, p.phone_number2,\n"
            + "    p.policy, p.start_address, p.update_at, p.type,\n"
            + "    p.utilities, p.store_id, p.status, p.owner_name, p.quantity_seat\n"
            + "ORDER BY p.owner_name DESC, start_time DESC, price ASC",
            nativeQuery = true)
    List<Product> findByKeyWord(@Param("key") String keyword);


    @Query(value = "SELECT  " +
            "    p.product_id,  " +
            "    p.create_at,  " +
            "    p.description,  " +
            "    p.end_address,  " +
            "    COALESCE(MIN(t.end_time), p.end_time) AS end_time,  " +
            "    MIN(t.travel_date) AS travel_date ,  " +
            "    p.license_plates,  " +
            "    p.name,  " +
            "    p.phone_number,  " +
            "    p.phone_number2,  " +
            "    p.policy,  " +
            "    COALESCE(MIN(t.price), p.price) AS price,  " +
            "    p.start_address,  " +
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
//            "    AND ( :start_time IS NULL OR COALESCE(t.start_time, p.start_time) >= :start_time )  " +
            "    AND ( :start_address IS NULL OR p.start_address LIKE CONCAT('%', :start_address, '%') )  " +
            "    AND ( :end_address IS NULL OR p.end_address LIKE CONCAT('%', :end_address, '%') )  " +
            "GROUP BY  " +
            "    p.product_id, p.create_at, p.description, p.end_address,  " +
            "    p.license_plates, p.name, p.phone_number, p.phone_number2,  " +
            "    p.policy, p.start_address, p.update_at, p.type,  " +
            "    p.utilities, p.store_id, p.status, p.owner_name, p.quantity_seat,  " +
            "    start_city, end_city " +
            "ORDER BY start_time DESC, price ASC",
            nativeQuery = true)
    List<Product> findByManyKeyWord(
            @Param("start_city") String startCity,
            @Param("end_city") String endCity,
//            @Param("start_time") LocalTime startTime,
//            @Param("date") LocalDate date,
            @Param("start_address") String startAddress,
            @Param("end_address") String endAddress);

}
