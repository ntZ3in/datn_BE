package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.dto.CreateOrderDTO;
import bookcarupdate.bookcar.dto.OrderDTO;
import bookcarupdate.bookcar.dto.UpdateOrderDTO;
import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.Order;
import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.User;
import bookcarupdate.bookcar.repositories.OrderRepository;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.repositories.UserRepository;
import bookcarupdate.bookcar.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    public Order createOrder(CreateOrderDTO createOrderDTO) {
        System.out.println(createOrderDTO);
        Order order = new Order();
        order.setCreatedAt(new Date());
        order.setOrderStatus(createOrderDTO.getOrderStatus());
        order.setMessage(createOrderDTO.getMessage());
        order.setPrice(createOrderDTO.getPrice());
        order.setQuantity(createOrderDTO.getQuantity());
        order.setTotalPrice(createOrderDTO.getTotalPrice());
        order.setDestinationAddress(createOrderDTO.getDestinationAddress());
        order.setPhoneNumber(createOrderDTO.getPhoneNumber());
        order.setPickTime(createOrderDTO.getPickTime());
        order.setPickUpAddress(createOrderDTO.getPickUpAddress());
        order.setLastUpdate(new Date());
        Product product = productRepository.findById(createOrderDTO.getId()).get();
        User user = userRepository.findByEmail(createOrderDTO.getEmailUser()).get();
        order.setProduct(product);
        order.setUser(user);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(UpdateOrderDTO updateOrderDTO, Long id) {
        Order order = orderRepository.findById(id).get();
        order.setOrderStatus(updateOrderDTO.getStatus());
        order.setLastUpdate(new Date());
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void deleteOrder(Long id) {
        if(orderRepository.findById(id).isPresent()){
            throw new CloudNotFoundException("Không tìm thấy đơn vé");
        }
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getAllOrderByIdStore(Long id) {
        return orderRepository.findAllByIdStore(id);
    }

    public List<Order> getAllOrderByEmailUser(String email) {
        return orderRepository.findAllByEmailUser(email);
    }
}
