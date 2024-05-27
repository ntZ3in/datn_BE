package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.dto.ProductDTO;
import bookcarupdate.bookcar.models.Product;
import org.springframework.data.domain.Page;

import java.time.LocalTime;
import java.util.List;

public interface ProductService {
    public Product addProduct(ProductDTO productDTO);
    public List<Product> getAllProductByIdStore(Long id);
    public Product updateProduct(Long id, ProductDTO productDTO);
    public Page<Product> getProductPagination(int pageof, int pagesize);

    public List<Product> findByKeyWord(String key);
    public List<Product> findByManyKeyWord(LocalTime start_time,String start_address, String end_address);

}
