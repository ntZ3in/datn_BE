package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.dto.CreateOrderDTO;
import bookcarupdate.bookcar.dto.GetOrderDTO;
import bookcarupdate.bookcar.dto.OrderDTO;
import bookcarupdate.bookcar.dto.UpdateOrderDTO;
import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.Order;
import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.Trip;
import bookcarupdate.bookcar.models.User;
import bookcarupdate.bookcar.repositories.OrderRepository;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.repositories.TripRepository;
import bookcarupdate.bookcar.repositories.UserRepository;
import bookcarupdate.bookcar.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
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

        Trip trip = tripRepository.findById(createOrderDTO.getId()).orElseThrow(()->new CloudNotFoundException("Trip not found"));
        if(trip.getRemainSeat() < createOrderDTO.getQuantity()){
            throw new RuntimeException("Not enough seats available");
        }
        trip.setRemainSeat(trip.getRemainSeat() - createOrderDTO.getQuantity());
        User user = userRepository.findByEmail(createOrderDTO.getEmailUser()).get();
        order.setTrip(trip);
        order.setUser(user);
        return orderRepository.save(order);
    }
    public Map<String, Object> createOrder2(CreateOrderDTO createOrderDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
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

            Trip trip = tripRepository.findById(createOrderDTO.getId()).orElse(null);
            if(trip == null) {
                response.put("status", "error");
                response.put("message", "Trip không tồn tại");
                return response;
            }

            if(trip.getRemainSeat() < createOrderDTO.getQuantity()){
                response.put("status", "error");
                response.put("message", "Số ghế không đủ");
                return response;
            }

            trip.setRemainSeat(trip.getRemainSeat() - createOrderDTO.getQuantity());
            tripRepository.save(trip);

            User user = userRepository.findByEmail(createOrderDTO.getEmailUser()).orElse(null);
            order.setTrip(trip);
            order.setUser(user);
            orderRepository.save(order);

            response.put("status", "success");
            response.put("order", order);
            return response;

        } catch(Exception e){
            response.put("status", "error");
            response.put("message", "Có lỗi xảy ra, vui lòng thử lại");
            return response;
        }
    }


    @Override
    public Order updateOrder(UpdateOrderDTO updateOrderDTO, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if( updateOrderDTO.getStatus().equalsIgnoreCase("Đã hủy")){
            Trip trip = order.getTrip();
            trip.setRemainSeat(trip.getRemainSeat() + order.getQuantity());
            tripRepository.save(trip);
        }
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
    public List<GetOrderDTO> getAllOrderByIdStore(Long id) {
        return orderRepository.findAllByIdStore(id);
    }

    public List<GetOrderDTO> getAllOrderByEmailUser(String email) {
        return orderRepository.findAllByEmailUser(email);
    }
}
