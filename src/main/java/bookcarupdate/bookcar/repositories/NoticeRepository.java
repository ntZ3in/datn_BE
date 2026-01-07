package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.dto.NoticeGetDTO;
import bookcarupdate.bookcar.models.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

//    @Query(value = "SELECT n.notice_id as noticeId, n.created_at as createdAt, n.title as title " +
//            ", n.content as content, n.expired as expired " +
//            ", p.product_name as productName, p.product_id as productId, p.bien_so_xe as bienSoXe " +
//            ", p.start_time as startTime " +
//            "FROM notice_tb n " +
//            "JOIN product_tb p ON n.product_id = p.product_id " +
//            "JOIN store_tb s ON p.store_id = s.store_id " +
//            "WHERE s.store_id = :store_id " +
//            "ORDER BY n.created_at DESC"
//            , nativeQuery = true)
//    List<NoticeDTO> getAllStoreNotice(@Param("store_id") Long store_id);

//    @Query(value = "SELECT n.* FROM notice_tb n ORDER BY n.last_update DESC", nativeQuery = true)
//    List<Notice> getAllActiveNotice();

    @Query(value = "select new bookcarupdate.bookcar.dto.NoticeGetDTO(nt.noticeID,p.productID,p.name, p.start_time, p.license_plates," +
            "nt.title, nt.content, nt.createdAt, nt.lastUpdate, nt.status)" +
            "from Notice as nt " +
            "INNER join Product as p ON (nt.product.productID = p.productID)" +
            "where nt.product.productID =:id")
    Optional<NoticeGetDTO> getNoticeByNoticeID(@Param("id") Long id);

    @Query(value = "select new bookcarupdate.bookcar.dto.NoticeGetDTO(" +
            "nt.noticeID,p.productID,p.name, " +
            "p.start_time, " +
            "p.license_plates, " +
            "nt.title, " +
            "nt.content, " +
            "nt.createdAt, " +
            "nt.lastUpdate, " +
            "nt.status) " +
            "from Notice nt " +
            "join nt.product p where p.store.storeID=:storeId")
    List<NoticeGetDTO> getAllNotices(@Param("storeId") Long storeId);

}
