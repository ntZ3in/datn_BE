package bookcarupdate.bookcar.controller;

import bookcarupdate.bookcar.dto.GetProductDTO;
import bookcarupdate.bookcar.dto.GetStoreDTO;
import bookcarupdate.bookcar.dto.ProductDTO;
import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.Order;
import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.User;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.services.OrderService;
import bookcarupdate.bookcar.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/seller/")
@RequiredArgsConstructor
public class SellerController {
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductRepository productRepository;
//    @PostMapping("/add-product")
//    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO product) {
//        System.out.println("Product: " + product);
//        Product product1 = productService.addProduct(product);
//        if (product1 == null) {
//            throw new CloudNotFoundException("Có lỗi xảy ra, vui lòng thử lại!");
//        }
//        return ResponseEntity.ok(product1);
//    }
//    @PutMapping("/update-product/{id}")
//    private ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO ) {
//        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
//    }
//    @DeleteMapping("/delete-product/{id}")
//    private ResponseEntity deleteProduct(@PathVariable("id") Long id) {
//        productRepository.deleteById(id);
//        return ResponseEntity.ok("Xoá thành công");
//    }
//
////    store
//@PostMapping("/get-all-product-by-idstore")
//private ResponseEntity<List<Product>> getAllProdut(@RequestBody GetProductDTO productDTO) {
//    System.out.println("id: "+productDTO.getId());
//    return ResponseEntity.ok(productService.getAllProductByIdStore(productDTO.getId()));
//}
//    @DeleteMapping("/delete-order/{id}")
//    private ResponseEntity deleteOrder(@PathVariable("id") Long id) {
//        orderService.deleteOrder(id);
//        return ResponseEntity.ok("Xoá thành công");
//    }
//
//    @GetMapping("/get-all-order-by-id-store/{idstore}")
//    private ResponseEntity<List<Order>> getOrderByIdStore(@PathVariable("idstore") Long id) {
//        return ResponseEntity.ok(orderService.getAllOrderByIdStore(id));
//    }


}

