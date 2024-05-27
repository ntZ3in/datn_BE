package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.dto.ProductDTO;
import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.Image;
import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.User;
import bookcarupdate.bookcar.repositories.ImageRepository;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.services.ProductService;
import bookcarupdate.bookcar.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(ProductDTO productDTO) {
        try {
            Product product = new Product();
            product.setCreateAt(new Date());
            product.setUpdateAt(new Date());
            product.setDescription(productDTO.getDescription());
            product.setEnd_address(productDTO.getEnd_address());
            product.setStart_address(productDTO.getStart_address());
            product.setStart_time(productDTO.getStart_time());
            product.setEnd_time(productDTO.getEnd_time());
            product.setLicense_plates(product.getLicense_plates());
            product.setName(productDTO.getName());
            product.setPhone_number(productDTO.getPhone_number());
            product.setPhone_number2(productDTO.getPhone_number2());
            product.setRemain_seat(Integer.parseInt(String.valueOf(productDTO.getRemain_seat())));
            product.setPrice(Double.parseDouble(String.valueOf(productDTO.getPrice())));
            product.setType(productDTO.getType());
            product.setPolicy(productDTO.getPolicy());
            product.setUtilities(productDTO.getUtilities());
            product.setLicense_plates(productDTO.getLicense_plates());
            product.setStatus(productDTO.getStatus());
            User user = userService.getCurrentUser(productDTO.getEmailUser());
            List<Image> images = new ArrayList<>();
            for (int i = 0; i < productDTO.getImages().size(); i++) {
                Image image = new Image();
                image.setImage_url(String.valueOf(productDTO.getImages().get(i).getImage_url()));
                images.add(image);
                image.setProduct(product);
            }
            product.setOwner_name(user.getStore().getStoreName());
            product.setImages(images);
            product.setStore(user.getStore());
            return productRepository.save(product);
        } catch (Exception ex) {
            throw new CloudNotFoundException("Id không hợp lệ");
        }
    }

    @Override
    public List<Product> getAllProductByIdStore(Long id) {
        return productRepository.findAllStoreProducts(id);
    }

    public Product getProductById(Long product_id) {
        Optional<Product> productOptional = productRepository.findById(product_id);
        return productOptional.get();
    }
    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        try {
            Product product = getProductById(id);
//            imageRepository.deleteAll(product.getImages());
//            List<Image> images0 = product.getImages();
//            for (int i = 0; i < images0.size(); i++) {
//                Image image = product.getImages().get(i);
//                imageRepository.deleteById(image.getId());
//            }
            product.setUpdateAt(new Date());
            product.setDescription(productDTO.getDescription());
            product.setEnd_address(productDTO.getEnd_address());
            product.setStart_address(productDTO.getStart_address());
            product.setStart_time(productDTO.getStart_time());
            product.setEnd_time(productDTO.getEnd_time());
            product.setLicense_plates(product.getLicense_plates());
            product.setName(productDTO.getName());
            product.setPhone_number(productDTO.getPhone_number());
            product.setPhone_number2(productDTO.getPhone_number2());
            product.setRemain_seat(Integer.parseInt(String.valueOf(productDTO.getRemain_seat())));
            product.setPrice(Double.parseDouble(String.valueOf(productDTO.getPrice())));
            product.setType(productDTO.getType());
            product.setPolicy(productDTO.getPolicy());
            product.setUtilities(productDTO.getUtilities());
            product.setLicense_plates(productDTO.getLicense_plates());
            product.setStatus(productDTO.getStatus());
//            List<Image> images = new ArrayList<>();
//            for (int i = 0; i < productDTO.getImages().size(); i++) {
//                Image image = new Image();
//                image.setImage_url(String.valueOf(productDTO.getImages().get(i).getImage_url()));
//                images.add(image);
//                image.setProduct(product);
//            }
//            product.setImages(images);
            return productRepository.save(product);
        } catch (Exception e) {
            throw new CloudNotFoundException("Không tìm thấy sản phẩm có id là: " + id);
        }
    }

    @Override
    public Page<Product> getProductPagination(int pageof, int pagesize) {
        return productRepository.findAll(PageRequest.of(pageof,pagesize));
    }

    @Override
    public List<Product> findByKeyWord(String key) {
        return productRepository.findByKeyWord(key);
    }

    @Override
    public List<Product> findByManyKeyWord(LocalTime start_time, String start_address, String end_address) {
        return productRepository.findByManyKeyWord(start_time,start_address,end_address);
    }
}
