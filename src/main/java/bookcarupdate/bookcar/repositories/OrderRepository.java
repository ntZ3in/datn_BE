package bookcarupdate.bookcar.repositories;

import bookcarupdate.bookcar.dto.CreateOrderDTO;
import bookcarupdate.bookcar.dto.GetOrderDTO;
import bookcarupdate.bookcar.dto.OrderDTO;
import bookcarupdate.bookcar.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
        select new bookcarupdate.bookcar.dto.GetOrderDTO(
            o.orderID,
            p.name,
            p.owner_name,
            new bookcarupdate.bookcar.dto.LocationDTO(
                o.pickLocation.name,
                o.pickLocation.lat,
                o.pickLocation.lng
            ),
            new bookcarupdate.bookcar.dto.LocationDTO(
                o.destinationLocation.name,
                o.destinationLocation.lat,
                o.destinationLocation.lng
            ),
            o.pickTime,
            o.message,
            o.quantity,
            o.phoneNumber,
            o.price,
            CAST (o.quantity * o.price as double ),
            o.orderStatus,
            t.tripId,
            o.createdAt
        )
        FROM Order o
        JOIN o.trip t
        JOIN t.product p
        JOIN p.store s
        WHERE s.storeID = :id
        ORDER BY o.createdAt DESC
        """)
    List<GetOrderDTO> findAllByIdStore(@Param("id") Long id);

    @Query("""
        select new bookcarupdate.bookcar.dto.GetOrderDTO(
            o.orderID,
            p.name,
            p.owner_name,
            new bookcarupdate.bookcar.dto.LocationDTO(
                o.pickLocation.name,
                o.pickLocation.lat,
                o.pickLocation.lng
            ),
            new bookcarupdate.bookcar.dto.LocationDTO(
                o.destinationLocation.name,
                o.destinationLocation.lat,
                o.destinationLocation.lng
            ),
            o.pickTime,
            o.message,
            o.quantity,
            o.phoneNumber,
            o.price,
            CAST (o.quantity * o.price as double ),
            o.orderStatus,
            t.tripId,
            o.createdAt
        )
        FROM Order o
        JOIN o.trip t
        JOIN t.product p
        WHERE o.user.email = :email order by o.createdAt desc
        """)
    List<GetOrderDTO> findAllByEmailUser(@Param("email") String email);
}
