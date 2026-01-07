package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.dto.GetOrderDTO;
import bookcarupdate.bookcar.dto.ProductSearchDTO;
import bookcarupdate.bookcar.models.Order;
import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.Store;
import bookcarupdate.bookcar.models.User;
import bookcarupdate.bookcar.repositories.OrderRepository;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.repositories.UserRepository;
import bookcarupdate.bookcar.services.OrderService;
import bookcarupdate.bookcar.services.ProductService;
import bookcarupdate.bookcar.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final StoreService storeService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final OrderService orderService;

    /**
     * Lấy thống kê tổng quan cho dashboard admin
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // Đếm tổng số sản phẩm
            long totalProducts = productRepository.count();
            
            // Đếm tổng số đơn hàng
            long totalOrders = orderRepository.count();
            
            // Đếm tổng số người dùng
            long totalUsers = userRepository.count();
            
            // Tính tổng doanh thu
                List<Order> allOrders = orderRepository.findAll();
                double totalRevenue = allOrders.stream()
                    .mapToDouble(Order::getTotalPrice)
                    .sum();
            
            // Đếm đơn hàng chờ xử lý (status = "Đang chờ")
            long pendingOrders = allOrders.stream()
                    .filter(order -> "Đang chờ".equals(order.getOrderStatus()))
                    .count();
            
            // Đếm sản phẩm đang hoạt động (status = "hoạt động")
            long activeProducts = productRepository.findAll().stream()
                    .filter(product -> "hoạt động".equals(product.getStatus()))
                    .count();
            
            stats.put("totalProducts", totalProducts);
            stats.put("totalOrders", totalOrders);
            stats.put("totalUsers", totalUsers);
            stats.put("totalRevenue", String.valueOf((long) totalRevenue));
            stats.put("pendingOrders", pendingOrders);
            stats.put("activeProducts", activeProducts);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Không thể lấy thống kê: " + e.getMessage()));
        }
    }

    /**
     * Lấy tất cả sản phẩm (cho admin)
     */
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Không thể lấy danh sách sản phẩm: " + e.getMessage()));
        }
    }

    /**
     * Lấy tất cả đơn hàng (cho admin)
     */
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Không thể lấy danh sách đơn hàng: " + e.getMessage()));
        }
    }

    /**
     * Lấy tất cả người dùng (cho admin)
     */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            
            // Tạo response với thông tin cần thiết và tổng số đơn hàng
            List<Map<String, Object>> userResponses = users.stream()
                    .map(user -> {
                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("user_id", user.getUserID());
                        userMap.put("user_name", user.getUsername());
                        userMap.put("email", user.getEmail());
                        userMap.put("phone_number", user.getPhone_number());
                        userMap.put("role", user.getRole().toString());
                        userMap.put("created_at", user.getCreatedAt());
                        
                        // Đếm số đơn hàng của user
                        int totalOrders = user.getOrderList() != null ? user.getOrderList().size() : 0;
                        userMap.put("total_orders", totalOrders);
                        
                        return userMap;
                    })
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(userResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Không thể lấy danh sách người dùng: " + e.getMessage()));
        }
    }

    /**
     * Lấy tất cả cửa hàng
     */
    @GetMapping("/stores")
    public ResponseEntity<?> getAllStores() {
        try {
            List<Store> stores = storeService.findAll();
            return ResponseEntity.ok(stores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Không thể lấy danh sách cửa hàng: " + e.getMessage()));
        }
    }
}
