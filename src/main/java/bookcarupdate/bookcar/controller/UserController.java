package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.dto.CreateOrderDTO;
import bookcarupdate.bookcar.dto.GetOrderDTO;
import bookcarupdate.bookcar.dto.UpdateOrderDTO;
import bookcarupdate.bookcar.models.Order;
import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.Store;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.repositories.ResponsiHandler;
import bookcarupdate.bookcar.repositories.StoreRepository;
import bookcarupdate.bookcar.services.OrderService;
import bookcarupdate.bookcar.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController {
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final ProductService productService;
    private final StoreRepository storeRepository;

    // tạm k dùng đến
    @PostMapping("/create-order")
    private ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        return ResponseEntity.ok(orderService.createOrder(createOrderDTO));
    }

    @PostMapping("/create-order2")
    private ResponseEntity<Map<String, Object>> createOrder2(@RequestBody CreateOrderDTO createOrderDTO) {
        return ResponseEntity.ok(orderService.createOrder2(createOrderDTO));
    }
    //
    @GetMapping("/get-all-order-by-email-user")
    private ResponseEntity<List<GetOrderDTO>> getOrderByEmailUser(Authentication authentication) {
        return ResponseEntity.ok(orderService.getAllOrderByEmailUser(authentication.getName()));
    }

}
