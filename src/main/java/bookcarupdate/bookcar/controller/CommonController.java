package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.dto.*;
import bookcarupdate.bookcar.models.*;
import bookcarupdate.bookcar.repositories.ResponsiHandler;
import bookcarupdate.bookcar.services.OrderService;
import bookcarupdate.bookcar.services.ProductService;
import bookcarupdate.bookcar.services.StoreService;
import bookcarupdate.bookcar.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/common/")
@RequiredArgsConstructor
// dùng chung cho cả USER và SELLER
public class CommonController {
    private final UserService userService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final ProductService productService;
    private final StoreService storeService;

    @GetMapping("/get-owner-name")
    private ResponseEntity<List<Store>> getAllStore() {
        return ResponseEntity.ok(storeService.findAll());
    }
    //
    @GetMapping("/get-order/{orderId}")
    private ResponseEntity<Optional<Order>> getOrder(@PathVariable("orderId") Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }
    //
    @PutMapping("/update-order/{orderId}")
    private ResponseEntity<Order> updateOrder(@PathVariable("orderId") Long id, @RequestBody UpdateOrderDTO updateOrderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(updateOrderDTO, id));
    }
    @DeleteMapping("/delete-order/{orderId}")
    private ResponseEntity deleteOrder(@PathVariable("orderId") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Xoá thành công");
    }
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            Authentication authentication,
            @RequestBody Map<String, String> body
    ) {
        String email = authentication.getName();
        String newPassword = body.get("password");

        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("Mật khẩu mới không được để trống");
        }

        try {
            User user = userService.getCurrentUser(email);
            // mã hóa mật khẩu bằng BCrypt trước khi lưu
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.save(user);

            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đổi mật khẩu thất bại: " + e.getMessage());
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<User> updateUser(
            Authentication authentication,
            @RequestBody UserUpdateRequest request) {

        User user = userService.getCurrentUser(authentication.getName());

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Cập nhật email, phone
        user.setEmail(request.getEmail());
        user.setPhone_number(request.getPhone_number());

        // Nếu có nhập mật khẩu mới thì mã hóa
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        }

        userService.save(user);

        return ResponseEntity.ok(user);
    }
    // cần token, lấy email từ authentication
    @GetMapping("/get-role")
    public ResponseEntity<Role> getRoleUser(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getCurrentUser(email).getRole());
    }

    @GetMapping("/get-user")
    private ResponseEntity<User> getUser(Authentication authentication) {
        String email = authentication.getName();
        System.out.println("email:" + email);
        System.out.println(userService.getCurrentUser(email).getUsername());
        return ResponseEntity.ok(userService.getCurrentUser(email));
    }

    @PostMapping("/get-store")
    private ResponseEntity<User> getIdStore(Authentication authentication) {
        String email = authentication.getName();
        System.out.println(email);
        return ResponseEntity.ok(userService.getCurrentUser(email));
    }
    // k cần token
    @PostMapping("/get-all-product")
    public ResponseEntity<List<ProductSearchDTO>> getAllProduct(@RequestBody(required = false) LocationDTO userLocation) {
        List<ProductSearchDTO> products = productService.findAll2(userLocation);
        for(ProductSearchDTO p : products){
            System.out.println(p.toString());
        }
        return ResponseEntity.ok(productService.findAll2(userLocation));
    }
    // k cần token
    @PostMapping("/get-all-product-pagi/{page}")
    public ResponseEntity<Object> getAllProductPagi(@PathVariable("page") Integer page, @RequestBody(required = false) LocationDTO userLocation) {
        int size = 10; // số bản ghi mỗi trang

        List<ProductSearchDTO> products = productService.findAllPagi(page, size, userLocation);
        List<ProductSearchDTO> productsAll = productService.findAll2(userLocation); // toàn bộ để tính pageNumber
        for(ProductSearchDTO p : productsAll){
            System.out.println(p.toString());
        }

        Map<String, Object> data = new HashMap<>();
        int pageNumber = (int) Math.ceil((double) productsAll.size() / size);

        data.put("pageNumber", pageNumber);
        data.put("data tong", productsAll);
        data.put("page", page);
        data.put("data", products);

        return ResponsiHandler.responsiBuider("Lấy dữ liệu thành công", HttpStatus.OK, data);
    }
    // k cần token
    @GetMapping("/get-store/{id}")
    private ResponseEntity<Optional<Store>> getStore(@PathVariable("id") Long id) {
        return ResponseEntity.ok(storeService.getStore(id));
    }
    // k cần token
    @GetMapping("/get-product/{id}")
    private ResponseEntity<Optional<Product>> getRoleUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }
    // k cần token
    @PostMapping("/search")
    private ResponseEntity<List<ProductSearchDTO>> findByKeyWord(@RequestBody(required = false) SearchRequest sr){
        System.out.println("key: " + sr.getKey()+", fromcity: " + sr.getFrom_city()+", tocity: "+ sr.getTo_city()+", starttime: "+ sr.getStart_time()+", date: "+ sr.getDate()+
                ", startadd: "+ sr.getStart_address()+", endadd:" + sr.getEnd_address());
        if (sr.getKey() != null) {
            for( ProductSearchDTO p : productService.findByKeyWord(sr.getKey().toLowerCase(), sr.getUserLocation())){
                System.out.println(p.toString());
            }
            return ResponseEntity.ok(productService.findByKeyWord(sr.getKey().toLowerCase(), sr.getUserLocation()));
        }
        for( ProductSearchDTO p : productService.findByManyKeyWord(sr.getFrom_city(), sr.getTo_city(), sr.getStart_time(), sr.getDate(), sr.getStart_address(), sr.getEnd_address(),sr.getUserLocation())){
            System.out.println(p.toString());
        }
        System.out.println("Chạy đến hàm search");
        return ResponseEntity.ok(productService.findByManyKeyWord(sr.getFrom_city(), sr.getTo_city(), sr.getStart_time(), sr.getDate(), sr.getStart_address(), sr.getEnd_address(),sr.getUserLocation()));
    }
}
