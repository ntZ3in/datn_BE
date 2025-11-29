package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.dto.*;
import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.*;
import bookcarupdate.bookcar.repositories.*;
import bookcarupdate.bookcar.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final StoreService storeService;
    private final ProductService productService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final NoticeService noticeService;
    private final ImageService imageService;

    // dùng cho trang quản lý product của store: ok
    @GetMapping("/get-all-product")
    public ResponseEntity<List<ProductSearchDTO>> getAllProduct() {
        List<ProductSearchDTO> products = productService.findAll2();
        for(ProductSearchDTO p : products){
            System.out.println(p.toString());
        }
        return ResponseEntity.ok(productService.findAll2());
    }

    @GetMapping("/get-all-product-pagi/{page}")
    public ResponseEntity<Object> getAllProductPagi(@PathVariable("page") Integer page) {
        int size = 10; // số bản ghi mỗi trang

        List<ProductSearchDTO> products = productService.findAllPagi(page, size);
        List<ProductSearchDTO> productsAll = productService.findAll2(); // toàn bộ để tính pageNumber

        Map<String, Object> data = new HashMap<>();
        int pageNumber = (int) Math.ceil((double) productsAll.size() / size);

        data.put("pageNumber", pageNumber);
        data.put("data tong", productsAll);
        data.put("page", page);
        data.put("data", products);

        return ResponsiHandler.responsiBuider("Lấy dữ liệu thành công", HttpStatus.OK, data);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/sign-up-seller")
    public ResponseEntity<Store> signUpSeller(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUpSeller(signUpRequest));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refresh(refreshTokenRequest));
    }

    @GetMapping("/get-role/{email}")
    private ResponseEntity<Role> getRoleUser(@PathVariable("email") String email) {
        System.out.println("email:" + email);
        return ResponseEntity.ok(userService.getCurrentUser(email).getRole());
    }


    @GetMapping("/get-user/{email}")
    private ResponseEntity<User> getUser(@PathVariable("email") String email) {
        System.out.println("email:" + email);
        System.out.println(userService.getCurrentUser(email).getUsername());
        return ResponseEntity.ok(userService.getCurrentUser(email));
    }

    @PostMapping("/get-store")
    private ResponseEntity<User> getIdStore(@RequestBody GetStoreDTO email) {
        System.out.println(email.getEmailUser());
        return ResponseEntity.ok(userService.getCurrentUser(email.getEmailUser()));
    }

    @GetMapping("/get-store/{id}")
    private ResponseEntity<Optional<Store>> getStore(@PathVariable("id") Long id) {
        return ResponseEntity.ok(storeService.getStore(id));
    }

    @GetMapping("/get-product/{id}")
    private ResponseEntity<Optional<Product>> getRoleUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

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
    @GetMapping("/get-order/{orderId}")
    private ResponseEntity<Optional<Order>> getOrder(@PathVariable("orderId") Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }
//
    @PutMapping("/update-order/{orderId}")
    private ResponseEntity<Order> updateOrder(@PathVariable("orderId") Long id, @RequestBody UpdateOrderDTO updateOrderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(updateOrderDTO, id));
    }
//
    @GetMapping("/get-all-order-by-email-user/{email}")
    private ResponseEntity<List<GetOrderDTO>> getOrderByEmailUser(@PathVariable("email") String email) {
        return ResponseEntity.ok(orderService.getAllOrderByEmailUser(email));
    }
//
    @GetMapping("/search")
    private ResponseEntity<List<ProductSearchDTO>> findByKeyWord(@RequestParam(value = "key", required = false) String key,
                                                        @RequestParam(value = "from_city", required = false) String startCity,
                                                        @RequestParam(value = "to_city", required = false) String endCity,
                                                        @RequestParam(value = "start_time", required = false) LocalTime startTime,
                                                        @RequestParam(value = "date", required = false) LocalDate date,
                                                        @RequestParam(value = "start_address", required = false) String startAddress,
                                                        @RequestParam(value = "end_address", required = false) String endAddress) {
        System.out.println("key: " + key+", fromcity: " + startCity+", tocity: "+ endCity+", starttime: "+ startTime+", date: "+ date+", startadd: "+ startAddress+", endadd:" + endAddress);
        if (key != null) {
            for( ProductSearchDTO p : productService.findByKeyWord(key)){
                System.out.println(p.toString());
            }
            return ResponseEntity.ok(productService.findByKeyWord(key));
        }
        for( ProductSearchDTO p : productService.findByManyKeyWord(startCity, endCity, startTime, date, startAddress, endAddress)){
            System.out.println(p.toString());
        }
        return ResponseEntity.ok(productService.findByManyKeyWord(startCity,endCity,startTime,date, startAddress, endAddress));
    }
//
    @GetMapping("/get-owner-name")
    private ResponseEntity<List<Store>> getAllStore() {
        return ResponseEntity.ok(storeService.findAll());
    }

    @PostMapping("/add-product")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO product) {
        System.out.println("Product: " + product);
        Product product1 = productService.addProduct(product);
        if (product1 == null) {
            throw new CloudNotFoundException("Có lỗi xảy ra, vui lòng thử lại!");
        }
        return ResponseEntity.ok(product1);
    }
    @PutMapping("/update-product/{id}")
    private ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO ) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }//

    @PutMapping("/update-status-product/{id}")
    private ResponseEntity<Product> updateStatusProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.updateStatusProduct(id));
    }

    @DeleteMapping("/delete-product/{id}")
    private ResponseEntity deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Xoá thành công");
    }
//
    @PostMapping("/get-all-product-by-idstore")
    private ResponseEntity<List<Product>> getAllProdut(@RequestBody GetProductDTO productDTO) {
        System.out.println("id: "+productDTO.getId());
        return ResponseEntity.ok(productService.getAllProductByIdStore(productDTO.getId()));
    }
    @DeleteMapping("/delete-order/{orderId}")
    private ResponseEntity deleteOrder(@PathVariable("orderId") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Xoá thành công");
    }
//
    @GetMapping("/get-all-order-by-id-store/{idstore}")
    private ResponseEntity<List<GetOrderDTO>> getOrderByIdStore(@PathVariable("idstore") Long id) {
        return ResponseEntity.ok(orderService.getAllOrderByIdStore(id));
    }

    @PutMapping("/change-password/{email}")
    public ResponseEntity<?> changePassword(
            @PathVariable("email") String email,
            @RequestBody Map<String, String> body
    ) {
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

    @PutMapping("/update-user/{email}")
    public ResponseEntity<User> updateUser(
            @PathVariable("email") String email,
            @RequestBody UserUpdateRequest request) {

        User user = userService.getCurrentUser(email);

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

    @PostMapping("/create-notification")
    public ResponseEntity<Notice> createNotification(@RequestBody CreateNoticeDTO dto) {
        return ResponseEntity.ok(noticeService.createNotice(dto));
    }

    @GetMapping("/get-all-notice-by-storeId/{storeId}")
    public ResponseEntity<List<NoticeGetDTO>> getAllNoticeByStoreId(@PathVariable("storeId") Long storeId){
        List<NoticeGetDTO> noticeGetDTOS = noticeService.getAllNotices(storeId);
        for( NoticeGetDTO nt : noticeGetDTOS){
            System.out.println(nt.toString());
        }
        return ResponseEntity.ok(noticeService.getAllNotices(storeId));
    }

    @DeleteMapping("/delete-notice/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable("id") Long id){
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-notice/{id}")
    public ResponseEntity<Notice> updateNotice(@PathVariable("id") Long id, @RequestBody CreateNoticeDTO dto){
        return ResponseEntity.ok(noticeService.updateNotice(id, dto));
    }

    @GetMapping("/{productId}/images")
    public ResponseEntity<List<ImageDTO>> getImagesByProduct(@PathVariable Long productId) {
        List<ImageDTO> images = imageService.getImagesByProductId(productId);
        return ResponseEntity.ok(images);
    }
}
