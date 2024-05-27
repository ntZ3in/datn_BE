package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query(value = "SELECT \n" +
            "    p.product_id, \n" +
            "    p.create_at, \n" +
            "    p.description, \n" +
            "    p.end_address, \n" +
            "    p.end_time, \n" +
            "    p.license_plates, \n" +
            "    p.name, \n" +
            "    p.phone_number, \n" +
            "    p.phone_number2, \n" +
            "    p.policy, \n" +
            "    p.price, \n" +
            "    p.start_address, \n" +
            "    p.start_time, \n" +
            "    p.update_at, \n" +
            "    p.type, \n" +
            "    p.utilities, \n" +
            "    p.store_id, \n" +
            "    p.status, \n" +
            "    p.owner_name, \n" +
            "    p.remain_seat - COALESCE(SUM(o.quantity), 0) AS remain_seat \n" +
            "FROM \n" +
            "    tblproduct p \n" +
            "LEFT JOIN \n" +
            "    tblorder o ON p.product_id = o.product_id \n" +
            "GROUP BY \n" +
            "    p.product_id, \n" +
            "    p.create_at, \n" +
            "    p.description, \n" +
            "    p.end_address, \n" +
            "    p.end_time, \n" +
            "    p.license_plates, \n" +
            "    p.name, \n" +
            "    p.phone_number, \n" +
            "    p.phone_number2, \n" +
            "    p.policy, \n" +
            "    p.price, \n" +
            "    p.start_address, \n" +
            "    p.start_time, \n" +
            "    p.update_at, \n" +
            "    p.type, \n" +
            "    p.utilities, \n" +
            "    p.store_id, \n" +
            "    p.status, \n" +
            "    p.owner_name, \n" +
            "    p.remain_seat \n" +
            "HAVING \n" +
            "    remain_seat > 0 OR remain_seat IS NULL;\n", nativeQuery = true)
    List<Product> findAll2();

    @Query(value = "SELECT \n" +
            "    p.product_id, \n" +
            "    p.create_at, \n" +
            "    p.description, \n" +
            "    p.end_address, \n" +
            "    p.end_time, \n" +
            "    p.license_plates, \n" +
            "    p.name, \n" +
            "    p.phone_number, \n" +
            "    p.phone_number2, \n" +
            "    p.policy, \n" +
            "    p.price, \n" +
            "    p.start_address, \n" +
            "    p.start_time, \n" +
            "    p.update_at, \n" +
            "    p.type, \n" +
            "    p.utilities, \n" +
            "    p.store_id, \n" +
            "    p.status, \n" +
            "    p.owner_name, \n" +
            "    p.remain_seat - COALESCE(SUM(o.quantity), 0) AS remain_seat \n" +
            "FROM \n" +
            "    tblproduct p \n" +
            "LEFT JOIN \n" +
            "    tblorder o ON p.product_id = o.product_id \n" +
            "GROUP BY \n" +
            "    p.product_id, \n" +
            "    p.create_at, \n" +
            "    p.description, \n" +
            "    p.end_address, \n" +
            "    p.end_time, \n" +
            "    p.license_plates, \n" +
            "    p.name, \n" +
            "    p.phone_number, \n" +
            "    p.phone_number2, \n" +
            "    p.policy, \n" +
            "    p.price, \n" +
            "    p.start_address, \n" +
            "    p.start_time, \n" +
            "    p.update_at, \n" +
            "    p.type, \n" +
            "    p.utilities, \n" +
            "    p.store_id, \n" +
            "    p.status, \n" +
            "    p.owner_name, \n" +
            "    p.remain_seat \n" +
            "HAVING \n" +
            "    remain_seat > 0 OR remain_seat IS NULL LIMIT :page OFFSET :number ;", nativeQuery = true)
    List<Product> findAllPagi(@Param("page") int page, @Param("number") int number);
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

    @Query(value = "SELECT \n" +
            "    p.product_id, \n" +
            "    p.create_at, \n" +
            "    p.description, \n" +
            "    p.end_address, \n" +
            "    p.end_time, \n" +
            "    p.license_plates, \n" +
            "    p.name, \n" +
            "    p.phone_number, \n" +
            "    p.phone_number2, \n" +
            "    p.policy, \n" +
            "    p.price, \n" +
            "    p.start_address, \n" +
            "    p.start_time, \n" +
            "    p.update_at, \n" +
            "    p.type, \n" +
            "    p.utilities, \n" +
            "    p.store_id, \n" +
            "    p.status, \n" +
            "    p.owner_name, \n" +
            "    p.remain_seat - COALESCE(SUM(o.quantity), 0) AS remain_seat \n" +
            "FROM \n" +
            "    tblproduct p \n" +
            "LEFT JOIN \n" +
            "    tblorder o ON p.product_id = o.product_id \n" +
            "WHERE \n" +
            "    p.name LIKE %:key% \n" +
            "    OR p.start_address LIKE %:key% \n" +
            "    OR p.end_address LIKE %:key% \n" +
            "    OR p.owner_name LIKE %:key% \n" +
            "GROUP BY \n" +
            "    p.product_id, \n" +
            "    p.create_at, \n" +
            "    p.description, \n" +
            "    p.end_address, \n" +
            "    p.end_time, \n" +
            "    p.license_plates, \n" +
            "    p.name, \n" +
            "    p.phone_number, \n" +
            "    p.phone_number2, \n" +
            "    p.policy, \n" +
            "    p.price, \n" +
            "    p.start_address, \n" +
            "    p.start_time, \n" +
            "    p.update_at, \n" +
            "    p.type, \n" +
            "    p.utilities, \n" +
            "    p.store_id, \n" +
            "    p.status, \n" +
            "    p.owner_name, \n" +
            "    p.remain_seat \n" +
            "ORDER BY \n" +
            "    p.start_time ASC;\n", nativeQuery = true)
    List<Product> findByKeyWord(@Param("key") String keyword);

    @Query(value = "SELECT p.product_id, p.create_at, p.description, p.end_address, p.end_time, p.license_plates, p.name, p.phone_number, p.phone_number2, p.policy, p.price, p.start_address, p.start_time, p.update_at, p.type, p.utilities, p.store_id, p.status, p.owner_name, p.remain_seat - SUM(o.quantity) AS remain_seat FROM tblproduct p INNER JOIN tblorder o ON p.product_id = o.product_id WHERE p.start_address LIKE  %:start_address% AND p.end_address LIKE %:end_address% AND p.start_time < :start_time AND p.end_time > :start_time p.product_id, \n" +
            "    p.create_at, \n" +
            "    p.description, \n" +
            "    p.end_address, \n" +
            "    p.end_time, \n" +
            "    p.license_plates, \n" +
            "    p.name, \n" +
            "    p.phone_number, \n" +
            "    p.phone_number2, \n" +
            "    p.policy, \n" +
            "    p.price, \n" +
            "    p.start_address, \n" +
            "    p.start_time, \n" +
            "    p.update_at, \n" +
            "    p.type, \n" +
            "    p.utilities, \n" +
            "    p.store_id, \n" +
            "    p.status, \n" +
            "    p.owner_name, \n" +
            " p.remain_seat  ORDER BY p.start_time ASC", nativeQuery = true)
    List<Product> findByManyKeyWord(
      @Param("start_time") LocalTime startTime,
      @Param("start_address") String startAddress,
      @Param("end_address") String endAddress
    );
}
