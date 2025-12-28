package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.dto.*;
import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.*;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.services.NoticeService;
import bookcarupdate.bookcar.services.OrderService;
import bookcarupdate.bookcar.services.ProductService;
import bookcarupdate.bookcar.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/seller/")
@RequiredArgsConstructor
public class SellerController {
    private final ProductService productService;
    private final OrderService orderService;
    private final NoticeService noticeService;
    private final StoreService storeService;

    @GetMapping("/test-auth")
    public ResponseEntity<?> testAuth(Authentication authentication) {

        System.out.println("EMAIL = " + authentication.getName());
        System.out.println("AUTHORITIES = " + authentication.getAuthorities());

        return ResponseEntity.ok(Map.of(
                "email", authentication.getName(),
                "roles", authentication.getAuthorities()
        ));
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
//    @PostMapping("/get-all-product-by-idstore")
//    private ResponseEntity<List<Product>> getAllProdut(@RequestBody GetProductDTO productDTO) {
//        System.out.println("id: "+productDTO.getId());
//        return ResponseEntity.ok(productService.getAllProductByIdStore(productDTO.getId()));
//    }
    @GetMapping("/products") // thay cho get-all-product-by-idstore
    public ResponseEntity<List<Product>> getMyProducts(Authentication authentication) {
        String email = authentication.getName();
        Store store = storeService.getStoreByOwnerEmail(email);
        return ResponseEntity.ok(productService.getAllProductByIdStore(store.getStoreID()));
    }

//    @GetMapping("/get-all-order-by-id-store/{idstore}")
//    private ResponseEntity<List<GetOrderDTO>> getOrderByIdStore(@PathVariable("idstore") Long id) {
//        return ResponseEntity.ok(orderService.getAllOrderByIdStore(id));
//    }
    @GetMapping("/orders")// thay get-all-order
    public ResponseEntity<List<GetOrderDTO>> getOrders(Authentication authentication) {
        String email = authentication.getName();
        Store store = storeService.getStoreByOwnerEmail(email);
        return ResponseEntity.ok(orderService.getAllOrderByIdStore(store.getStoreID()));
    }


    @PostMapping("/create-notification")
    public ResponseEntity<Notice> createNotification(@RequestBody CreateNoticeDTO dto) {
        return ResponseEntity.ok(noticeService.createNotice(dto));
    }

//    @GetMapping("/get-all-notice-by-storeId/{storeId}")
//    public ResponseEntity<List<NoticeGetDTO>> getAllNoticeByStoreId(@PathVariable("storeId") Long storeId){
//        List<NoticeGetDTO> noticeGetDTOS = noticeService.getAllNotices(storeId);
//        for( NoticeGetDTO nt : noticeGetDTOS){
//            System.out.println(nt.toString());
//        }
//        return ResponseEntity.ok(noticeService.getAllNotices(storeId));
//    }
    @GetMapping("/notices")// thay get-all-notice
    public ResponseEntity<List<NoticeGetDTO>> getNotices(Authentication authentication) {
        String email = authentication.getName();
        Store store = storeService.getStoreByOwnerEmail(email);
        return ResponseEntity.ok(noticeService.getAllNotices(store.getStoreID()));
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
}

