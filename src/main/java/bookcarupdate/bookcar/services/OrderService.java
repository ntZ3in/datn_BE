package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.dto.CreateOrderDTO;
import bookcarupdate.bookcar.dto.OrderDTO;
import bookcarupdate.bookcar.dto.UpdateOrderDTO;
import bookcarupdate.bookcar.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public Order createOrder(CreateOrderDTO createOrderDTO);

    public Order updateOrder(UpdateOrderDTO updateOrderDTO, Long id);

    public Optional<Order> getOrder(Long id);
    public void deleteOrder(Long id);

    public List<Order> getAllOrderByIdStore(Long id);
    public List<Order> getAllOrderByEmailUser(String email);
}

