package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.dto.CreateOrderDTO;
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

    //    Product
//    @GetMapping("/get-all-product")
//    public ResponseEntity<List<Product>> getAllProduct() {
//        return ResponseEntity.ok(productRepository.findAll2());
//    }
//
//    @GetMapping("/get-all-product-pagi/{page}")
//    public ResponseEntity<Object> getAllProductPagi(@PathVariable("page") Integer page) {
//        System.out.println(page);
//        System.out.println((page-1)*20);
//        int number = (int)(page-1)*20;
//        List<Product> products = productRepository.findAllPagi(page, number);
//        List<Product> products1 = productRepository.findAll2();
//        Map<String, Object> data = new HashMap<>();
//        int pageNumber = (int) Math.ceil((double) products1.size() / 20);
//        data.put("pageNumber", pageNumber);
//        data.put("page", page);
//        data.put("data", products);
//        return ResponsiHandler.responsiBuider("Lấy dữ liệu thành công", HttpStatus.OK, data);
//    }
//    @GetMapping("/get-product/{id}")
//    private ResponseEntity<Optional<Product>> getRoleUser(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(productRepository.findById(id));
//    }
//
//    @PostMapping("/create-order")
//    private ResponseEntity<Order> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
//        return ResponseEntity.ok(orderService.createOrder(createOrderDTO));
//    }
//
//    @GetMapping("/get-order/{id}")
//    private ResponseEntity<Optional<Order>> getOrder(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(orderService.getOrder(id));
//    }
//
//    @PutMapping("/update-order/{id}")
//    private ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody UpdateOrderDTO updateOrderDTO) {
//        return ResponseEntity.ok(orderService.updateOrder(updateOrderDTO, id));
//    }
//
//    @GetMapping("/get-all-order-by-email-user/{email}")
//    private ResponseEntity<List<Order>> getOrderByEmailUser(@PathVariable("email") String email) {
//        return ResponseEntity.ok(orderService.getAllOrderByEmailUser(email));
//    }
//
//    @GetMapping("/search")
//    private ResponseEntity<List<Product>> findByKeyWord(@RequestParam(value = "key", required = false) String key, @RequestParam(value = "start_time", required = false) LocalTime start_time, @RequestParam(value = "start_address", required = false) String start_address, @RequestParam(value = "end_address", required = false) String end_address) {
//        System.out.println(start_address + " " + end_address + " " + start_time);
//        if (key != null) {
//            return ResponseEntity.ok(productService.findByKeyWord(key));
//        }
////        return null;
//        return ResponseEntity.ok(productService.findByManyKeyWord(start_time, start_address, end_address));
//    }
//
//    @GetMapping("/get-owner-name")
//    private ResponseEntity<List<Store>> getAllStore() {
//        return ResponseEntity.ok(storeRepository.findAll());
//    }
}
