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
    @Query(value = "SELECT p.* FROM product_tb p " +
            "WHERE EXISTS ( " +
            "SELECT 1 FROM stop_tb s1 " +
            "WHERE s1.product_id = p.product_id " +
            "AND s1.stop_time BETWEEN :startTime1 AND :startTime2 " +
            "AND s1.stop_address LIKE %:startAddress%) " +
            "AND EXISTS (" +
            "SELECT 1 FROM stop_tb s2 " +
            "WHERE s2.product_id = p.product_id " +
            "AND s2.stop_time > :startTime2 " +
            "AND s2.stop_address LIKE %:endAddress%) " +
            "ORDER BY p.start_time ASC"
            , nativeQuery = true)
    List<Product> findProductsByTimeAndAddress(
            @Param("startTime1") LocalTime startTime1,
            @Param("startTime2") LocalTime startTime2,
            @Param("startAddress") String startAddress,
            @Param("endAddress") String endAddress
    );

//    @Query(value = """
//        SELECT
//            -- PRODUCT
//            p.product_id              AS productID,
//            p.license_plates,
//            p.description,
//            p.phone_number,
//            p.phone_number2,
//            p.start_address,
//            p.end_address,
//            p.start_time,
//            p.end_time,
//            p.price,
//            p.name,
//            p.quantity_seat,
//            p.policy,
//            p.utilities,
//            p.type,
//            p.create_at               AS createAt,
//            p.update_at               AS updateAt,
//            p.status,
//            p.owner_name,
//            p.store_id,
//
//            -- TRIP
//            t.trip_id,
//            t.travel_date,
//            t.start_time              AS trip_start_time,
//            t.end_time                AS trip_end_time,
//            t.price                   AS trip_price,
//            t.remain_seat,
//            t.status                  AS trip_status
//        FROM tblproduct p
//        LEFT JOIN tbltrip t ON p.product_id = t.product_id
//        where t.travel_date = curdate() and t.start_time >= curtime()
//        ORDER BY p.create_at DESC
//        """, nativeQuery = true)
//    List<ProductSearchDTO> findAll2();//ok

    @Query("SELECT new bookcarupdate.bookcar.dto.ProductSearchDTO(" +
            "p.productID, p.license_plates, p.description, p.phone_number, p.phone_number2, " +
            "p.start_address, p.end_address, p.start_time, p.end_time, p.price, p.name, " +
            "p.quantity_seat, p.policy, p.utilities, p.type, p.createAt, p.updateAt, " +
            "p.status, p.owner_name, p.store.storeID, " +
            "t.tripId, t.travelDate, t.startTime, t.endTime, t.price, t.remainSeat, t.status) " +
            "FROM Product p LEFT JOIN Trip t ON p.productID = t.product.productID " +
            "WHERE t.travelDate = CURRENT_DATE AND t.startTime >= CURRENT_TIME " +
            "ORDER BY p.createAt DESC")
    List<ProductSearchDTO> findAll2();

    @Query("SELECT new bookcarupdate.bookcar.dto.ProductSearchDTO(" +
            "p.productID, p.license_plates, p.description, p.phone_number, p.phone_number2, " +
            "p.start_address, p.end_address, p.start_time, p.end_time, p.price, p.name, " +
            "p.quantity_seat, p.policy, p.utilities, p.type, p.createAt, p.updateAt, " +
            "p.status, p.owner_name, p.store.storeID, " +
            "t.tripId, t.travelDate, t.startTime, t.endTime, t.price, t.remainSeat, t.status) " +
            "FROM Product p LEFT JOIN Trip t ON p.productID = t.product.productID " +
            "WHERE t.travelDate = CURRENT_DATE AND t.startTime >= CURRENT_TIME " +
            "ORDER BY p.createAt DESC")
    Page<ProductSearchDTO> findAllPagi(Pageable pageable);



//    @Query(value = """
//        SELECT
//            -- PRODUCT
//            p.product_id              AS productID,
//            p.license_plates,
//            p.description,
//            p.phone_number,
//            p.phone_number2,
//            p.start_address,
//            p.end_address,
//            p.start_time,
//            p.end_time,
//            p.price,
//            p.name,
//            p.quantity_seat,
//            p.policy,
//            p.utilities,
//            p.type,
//            p.create_at               AS createAt,
//            p.update_at               AS updateAt,
//            p.status,
//            p.owner_name,
//            p.store_id,
//
//            -- TRIP
//            t.trip_id,
//            t.travel_date,
//            t.start_time              AS trip_start_time,
//            t.end_time                AS trip_end_time,
//            t.price                   AS trip_price,
//            t.remain_seat,
//            t.status                  AS trip_status
//        FROM tblproduct p
//        LEFT JOIN tbltrip t ON p.product_id = t.product_id
//        WHERE t.travel_date = curdate() and t.start_time >= curtime()
//        ORDER BY p.create_at DESC
//        LIMIT :page OFFSET :number
//        """, nativeQuery = true)
//    List<ProductSearchDTO> findAllPagi(@Param("page") int page, @Param("number") int number);//ok


    @Query(value = "SELECT p.* FROM product_tb p WHERE display = true and deleted = false", nativeQuery = true)
    List<Product> findAllActiveProducts();

    @Query(value = "SELECT p.* FROM tblproduct p WHERE store_id = :store_id"
            , nativeQuery = true)
    List<Product> findAllStoreProducts(Long store_id);


//    @Query(value =  "SELECT DISTINCT p.* " +
//            "FROM product_tb p " +
//            "INNER JOIN stop_tb s ON p.product_id = s.product_id " +
//            "INNER JOIN store_tb st ON p.store_id = st.store_id " +
//            "WHERE (s.stop_address LIKE %:keyword% OR p.start_address LIKE %:keyword% " +
//            "OR p.end_address LIKE %:keyword% OR st.store_name LIKE %:keyword% " +
//            "OR p.bien_so_xe LIKE %:keyword%) " +
//            "ORDER BY p.start_time ASC"
//            , nativeQuery = true)
//    List<Product> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "" +
            "SELECT \n" +
            "    p.product_id,\n" +
            "    p.create_at,\n" +
            "    p.description,\n" +
            "    p.end_address,\n" +
            "    COALESCE(t.end_time, p.end_time) AS end_time,\n" +
            "    p.license_plates,\n" +
            "    p.name,\n" +
            "    p.phone_number,\n" +
            "    p.phone_number2,\n" +
            "    p.policy,\n" +
            "    COALESCE(t.price, p.price) AS price,\n" +
            "    p.start_address,\n" +
            "    COALESCE(t.start_time, p.start_time) AS start_time,\n" +
            "    p.update_at,\n" +
            "    p.type,\n" +
            "    p.utilities,\n" +
            "    p.store_id,\n" +
            "    p.status,\n" +
            "    p.owner_name,\n" +
            "    p.quantity_seat,\n" +
            "    p.quantity_seat - COALESCE(SUM(o.quantity), 0) AS remain_seat\n" +
            "FROM tblproduct p\n" +
            "LEFT JOIN tbltrip t ON p.product_id = t.product_id\n" +
            "LEFT JOIN tblorder o ON o.trip_id=t.trip_id \n" +
            "WHERE p.owner_name LIKE %:key%\n" +
            "  AND t.travel_date = CURDATE() and t.start_time >= curtime()\n" +
            "GROUP BY \n" +
            "    p.product_id, p.create_at, p.description, p.end_address,\n" +
            "    end_time, p.license_plates, p.name, p.phone_number, p.phone_number2,\n" +
            "    p.policy, price, p.start_address, start_time, p.update_at, p.type,\n" +
            "    p.utilities, p.store_id, p.status, p.owner_name, p.quantity_seat\n" +
            "ORDER BY p.owner_name DESC",
            nativeQuery = true)
    List<Product> findByKeyWord(@Param("key") String keyword);

    @Query(value = "SELECT \n" +
            "    p.product_id, \n" +
            "    p.create_at, \n" +
            "    p.description, \n" +
            "    p.end_address, \n" +
            "    COALESCE(t.end_time, p.end_time) AS end_time, \n" +
            "t.travel_date" +
            "    p.license_plates, \n" +
            "    p.name, \n" +
            "    p.phone_number, \n" +
            "    p.phone_number2, \n" +
            "    p.policy, \n" +
            "    COALESCE(t.price, p.price) AS price, \n" +
            "    p.start_address, \n" +
            "    COALESCE(t.start_time, p.start_time) AS start_time, \n" +
            "    p.update_at, \n" +
            "    p.type, \n" +
            "    p.utilities, \n" +
            "    p.store_id, \n" +
            "    p.status, \n" +
            "    p.owner_name, \n" +
            "    p.quantity_seat, \n" +
            "    TRIM(SUBSTRING_INDEX(p.name, '-', 1)) AS start_city, \n" +
            "    TRIM(SUBSTRING_INDEX(p.name, '-', -1)) AS end_city, \n" +
            "    p.quantity_seat - COALESCE(SUM(o.quantity), 0) AS remain_seat \n" +
            "FROM tblproduct p \n" +
            "LEFT JOIN tbltrip t ON p.product_id = t.product_id \n" +
            "LEFT JOIN tblorder o ON o.trip_id = t.trip_id \n" +
            "WHERE \n" +
            "    t.travel_date = :date \n" +  // date bắt buộc từ trip
            "    AND TRIM(SUBSTRING_INDEX(p.name, '-', 1)) LIKE %:start_city% \n" +
            "    AND TRIM(SUBSTRING_INDEX(p.name, '-', -1)) LIKE %:end_city% \n" +
            "    AND p.start_address LIKE %:start_address% \n" +
            "    AND p.end_address LIKE %:end_address% \n" +
            "    AND (:start_time IS NULL OR COALESCE(t.start_time, p.start_time) like :start_time%) \n" +
            "GROUP BY \n" +
            "    p.product_id, p.create_at, p.description, p.end_address, \n" +
            "    end_time, p.license_plates, p.name, p.phone_number, p.phone_number2, \n" +
            "    p.policy, price, p.start_address, start_time, p.update_at, p.type, \n" +
            "    p.utilities, p.store_id, p.status, p.owner_name, p.quantity_seat, \n" +
            "    start_city, end_city,t.travel_date \n" +
            "ORDER BY price ASC",
            nativeQuery = true)
    List<Product> findByManyKeyWord(@Param("start_city") String startCity,
                                    @Param("end_city") String endCity,
                                    @Param("start_time") LocalTime startTime,
                                    @Param("date") LocalDate date,
                                    @Param("start_address") String startAddress,
                                    @Param("end_address") String endAddress);

}
