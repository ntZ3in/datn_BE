package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.dto.ProductDTO;
import bookcarupdate.bookcar.dto.ProductSearchDTO;
import bookcarupdate.bookcar.models.Product;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Product addProduct(ProductDTO productDTO);
    public List<Product> getAllProductByIdStore(Long id);
    public Product updateProduct(Long id, ProductDTO productDTO);
    public Page<Product> getProductPagination(int pageof, int pagesize);

    public List<ProductSearchDTO> findByKeyWord(String key);
    public List<ProductSearchDTO> findByManyKeyWord(String startCity, String endCity, LocalTime startTime, LocalDate date, String startAddress, String endAddress);

    public Optional<Product> findById(Long id);
    public List<ProductSearchDTO> findAllPagi(int page, int number);
    public List<ProductSearchDTO> findAll2();
    public void deleteProduct(Long id);
}
