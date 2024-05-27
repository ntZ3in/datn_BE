package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.dto.*;
import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.*;
import bookcarupdate.bookcar.repositories.*;
import bookcarupdate.bookcar.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final StoreService storeService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final OrderService orderService;
    private final StoreRepository storeRepository;

    @GetMapping("/get-all-product")
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(productRepository.findAll2());
    }

    @GetMapping("/get-all-product-pagi/{page}")
    public ResponseEntity<Object> getAllProductPagi(@PathVariable("page") Integer page) {
        int number = (int)(page-1)*10;
        List<Product> products = productRepository.findAllPagi(10, number);
        List<Product> products1 = productRepository.findAll2();
        Map<String, Object> data = new HashMap<>();
        int pageNumber = (int) Math.ceil((double) products1.size() / 10);
        data.put("pageNumber", pageNumber);
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
        return ResponseEntity.ok(productRepository.findById(id));
    }

    @PostMapping("/create-order")
    private ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        return ResponseEntity.ok(orderService.createOrder(createOrderDTO));
    }

    @GetMapping("/get-order/{id}")
    private ResponseEntity<Optional<Order>> getOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PutMapping("/update-order/{id}")
    private ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody UpdateOrderDTO updateOrderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(updateOrderDTO, id));
    }

    @GetMapping("/get-all-order-by-email-user/{email}")
    private ResponseEntity<List<Order>> getOrderByEmailUser(@PathVariable("email") String email) {
        return ResponseEntity.ok(orderService.getAllOrderByEmailUser(email));
    }

    @GetMapping("/search")
    private ResponseEntity<List<Product>> findByKeyWord(@RequestParam(value = "key", required = false) String key, @RequestParam(value = "start_time", required = false) LocalTime start_time, @RequestParam(value = "start_address", required = false) String start_address, @RequestParam(value = "end_address", required = false) String end_address) {
        System.out.println(start_address + " " + end_address + " " + start_time);
        if (key != null) {
            return ResponseEntity.ok(productService.findByKeyWord(key));
        }
//        return null;
        return ResponseEntity.ok(productService.findByManyKeyWord(start_time, start_address, end_address));
    }

    @GetMapping("/get-owner-name")
    private ResponseEntity<List<Store>> getAllStore() {
        return ResponseEntity.ok(storeRepository.findAll());
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
    }
    @DeleteMapping("/delete-product/{id}")
    private ResponseEntity deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok("Xoá thành công");
    }

    //    store
    @PostMapping("/get-all-product-by-idstore")
    private ResponseEntity<List<Product>> getAllProdut(@RequestBody GetProductDTO productDTO) {
        System.out.println("id: "+productDTO.getId());
        return ResponseEntity.ok(productService.getAllProductByIdStore(productDTO.getId()));
    }
    @DeleteMapping("/delete-order/{id}")
    private ResponseEntity deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Xoá thành công");
    }

    @GetMapping("/get-all-order-by-id-store/{idstore}")
    private ResponseEntity<List<Order>> getOrderByIdStore(@PathVariable("idstore") Long id) {
        return ResponseEntity.ok(orderService.getAllOrderByIdStore(id));
    }
}
