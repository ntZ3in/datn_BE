package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.dto.CreateNoticeDTO;
import bookcarupdate.bookcar.dto.ImageDTO;
import bookcarupdate.bookcar.dto.ProductDTO;
import bookcarupdate.bookcar.dto.ProductSearchDTO;
import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.*;
import bookcarupdate.bookcar.repositories.ImageRepository;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.services.ProductService;
import bookcarupdate.bookcar.services.TripService;
import bookcarupdate.bookcar.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
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
    private final TripService tripService;

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
            product.setQuantity_seat(Integer.parseInt(String.valueOf(productDTO.getQuantity_seat())));
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
    public Product updateStatusProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("Not found"));
        if(product.getStatus().equalsIgnoreCase("Hiện")){
            product.setStatus("Ẩn");
        }else {
            product.setStatus("Hiện");
        }
        return productRepository.save(product);
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
            product.setQuantity_seat(Integer.parseInt(String.valueOf(productDTO.getQuantity_seat())));
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
    public List<ProductSearchDTO> findByKeyWord(String key) {
        List<Product> products = productRepository.findByKeyWord(key);
        List<ProductSearchDTO> result = new ArrayList<>();
        for(Product p : products){
            Trip trip = tripService.getOrCreateTrip(p.getProductID(),LocalDate.now(), LocalTime.now())
                    .orElseThrow(() -> new RuntimeException("Not found trip"));
            result.add(mapToDTO(p,trip));
        }
        return result;
    }

    @Override
    public List<ProductSearchDTO> findByManyKeyWord(String startCity, String endCity, LocalTime startTime, LocalDate date,String startAddress,String endAddress) {
        List<Product> products = productRepository.findByManyKeyWord(startCity,endCity, startAddress, endAddress);
        List<ProductSearchDTO> result = new ArrayList<>();
        for(Product p : products){
            Optional<Trip> trip = tripService.getOrCreateTrip(p.getProductID(),date, startTime);
            if(trip.isPresent()){
                System.out.println("trip tim duoc dang co: "+ trip.get().getTripId());
                result.add(mapToDTO(p,trip.get()));
            }else {
                System.out.println("k co trip tuong ung");
            }
        }
        return result;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductSearchDTO> findAll2() {
        return productRepository.findAll2();
    }

    @Override
    public List<ProductSearchDTO> findAllPagi(int page, int number) {
        if (page < 1) page = 1;
        if (number < 1) number = 10;

        Pageable pageable = PageRequest.of(page - 1, number);
        return productRepository.findAllPagi(pageable).getContent();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductSearchDTO mapToDTO(Product product, Trip trip) {
        ProductSearchDTO dto = new ProductSearchDTO();

        // Product info
        dto.setProductID(product.getProductID());
        dto.setLicense_plates(product.getLicense_plates());
        dto.setDescription(product.getDescription());
        dto.setPhone_number(product.getPhone_number());
        dto.setPhone_number2(product.getPhone_number2());
        dto.setStart_address(product.getStart_address());
        dto.setEnd_address(product.getEnd_address());
        dto.setStart_time(product.getStart_time());
        dto.setEnd_time(product.getEnd_time());
        dto.setPrice(product.getPrice());
        dto.setName(product.getName());
        dto.setQuantity_seat(product.getQuantity_seat());
        dto.setPolicy(product.getPolicy());
        dto.setUtilities(product.getUtilities());
        dto.setType(product.getType());
        dto.setCreateAt(product.getCreateAt());
        dto.setUpdateAt(product.getUpdateAt());
        dto.setStatus(product.getStatus());
        dto.setOwner_name(product.getOwner_name());
        dto.setStore_id(product.getStore().getStoreID());


        // Trip info
        dto.setTrip_id(trip.getTripId());
        dto.setTravel_date(trip.getTravelDate());
        dto.setTrip_start_time(trip.getStartTime());
        dto.setTrip_end_time(trip.getEndTime());
        dto.setTrip_price(trip.getPrice());
        dto.setRemain_seat(trip.getRemainSeat());
        dto.setTrip_status(trip.getStatus());// TRUE FALSE

        return dto;
    }
}
