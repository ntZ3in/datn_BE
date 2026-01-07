package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.dto.*;
import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.*;
import bookcarupdate.bookcar.repositories.ImageRepository;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.repositories.StopRepository;
import bookcarupdate.bookcar.repositories.TripRepository;
import bookcarupdate.bookcar.services.LocationService;
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
import java.util.*;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final TripService tripService;
    private final LocationService locationService;
    private final TripRepository tripRepository;
    private final StopRepository stopRepository;

    // khi thêm chỉ có 1 số thông tin product + ảnh của nó
    @Override
    public Product addProduct(ProductDTO productDTO) {
        try {
            Product product = new Product();
            product.setCreateAt(new Date());
            product.setUpdateAt(new Date());
            product.setDescription(productDTO.getDescription());
            // tìm hoặc tạo địa điểm bắt đầu
            Location startLocation = locationService.findOrCreate(
                    productDTO.getStartLocation().getName(),
                    productDTO.getStartLocation().getLat(),
                    productDTO.getStartLocation().getLng()
            );

            // tìm hoặc tạo địa điểm kết thúc
            Location endLocation = locationService.findOrCreate(
                    productDTO.getEndLocation().getName(),
                    productDTO.getEndLocation().getLat(),
                    productDTO.getEndLocation().getLng()
            );
            product.setStart_time(productDTO.getStart_time());
            product.setEnd_time(productDTO.getEnd_time());
            product.setLicense_plates(productDTO.getLicense_plates());
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
            List<Stop> stopList = new ArrayList<>();
            if (productDTO.getStopDTOS() != null) {
                for (StopDTO sd : productDTO.getStopDTOS()) {
                    Stop stop = new Stop();
                    stop.setStopTime(sd.getStop_time());
                    stop.setDeleted(sd.isDeleted());

                    Location location = locationService.findOrCreate(sd.getLocation().getName(),
                            sd.getLocation().getLat(), sd.getLocation().getLng());
                    stop.setLocation(location);
                    stop.setType(sd.getType());
                    stopList.add(stop);
                    stop.setProduct(product);
                }
            }
            product.setOwner_name(user.getStore().getStoreName());
            product.setImages(images);
            product.setStore(user.getStore());
            product.setStartLocation(startLocation);
            product.setEndLocation(endLocation);
            product.setStopList(stopList);
            return  productRepository.save(product);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
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
            product.setUpdateAt(new Date());
            product.setDescription(productDTO.getDescription());
            // tìm hoặc tạo địa điểm bắt đầu
            Location startLocation = locationService.findOrCreate(
                    productDTO.getStartLocation().getName(),
                    productDTO.getStartLocation().getLat(),
                    productDTO.getStartLocation().getLng()
            );

            // tìm hoặc tạo địa điểm kết thúc
            Location endLocation = locationService.findOrCreate(
                    productDTO.getEndLocation().getName(),
                    productDTO.getEndLocation().getLat(),
                    productDTO.getEndLocation().getLng()
            );
            product.setStartLocation(startLocation);
            product.setEndLocation(endLocation);

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
            // Xử lý stop points
            List<Stop> currentStops = product.getStopList() != null ? product.getStopList() : new ArrayList<>();
            currentStops.clear();
            if (productDTO.getStopDTOS() != null) {
                for (StopDTO stopDTO : productDTO.getStopDTOS()) {
                    Stop stop = new Stop();
                    // Xử lý location của stop
                    Location stopLocation = locationService.findOrCreate(
                            stopDTO.getLocation().getName(),
                            stopDTO.getLocation().getLat(),
                            stopDTO.getLocation().getLng()
                    );
                    stop.setLocation(stopLocation);
                    stop.setStopTime(stopDTO.getStop_time());
                    stop.setType(stopDTO.getType());
                    stop.setDeleted(stopDTO.isDeleted());
                    stop.setProduct(product);
                    currentStops.add(stop);
                }
            }
            product.setStopList(currentStops);
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    @Override
    public Page<Product> getProductPagination(int pageof, int pagesize) {
        return productRepository.findAll(PageRequest.of(pageof,pagesize));
    }

    @Override
    public List<ProductSearchDTO> findByKeyWord(String key, LocationDTO userLocation) {
        List<Product> products = productRepository.findByKeyWord(key);
        List<ProductSearchDTO> result = new ArrayList<>();
        for(Product p : products){
            Optional<Trip> trip = tripService.getOrCreateTrip(p.getProductID(),LocalDate.now(), LocalTime.now());
            if(trip.isEmpty()) continue;
            result.add(mapToDTO(p,trip.get(), userLocation));
        }
        if(result.isEmpty()) return Collections.emptyList();
        return result;
    }

    @Override
    public List<ProductSearchDTO> findByManyKeyWord(String fromCity, String toCity, LocalTime startTime, LocalDate date,LocationDTO startAddress,
                                                    LocationDTO endAddress, LocationDTO userLocation) {
        List<Product> products = productRepository.findByManyKeyWord(fromCity, toCity);
        List<ProductSearchDTO> result = new ArrayList<>();

        boolean hasFullParams =
                (startTime != null && date != null && startAddress != null && endAddress != null);
        boolean noStartAddress = (startAddress == null);

        // Nếu dùng vị trí hiện tại của user ( k có start_address)
        List<ProductDistanceWrapper> suggestList = new ArrayList<>();
        for (Product p : products){
            Optional<Trip> tripOpt = tripService.getOrCreateTrip(p.getProductID(), date, startTime);
            if(tripOpt.isEmpty()) continue;
            Trip trip = tripOpt.get();

            // Tìm chính xác ra kết quả
            if(hasFullParams){
                // tính khoảng cách
                double dStart = distance(startAddress.getLat(), startAddress.getLng(),
                        p.getStartLocation().getLat(), p.getStartLocation().getLng());
                double dEnd = distance(endAddress.getLat(), endAddress.getLng(),
                        p.getEndLocation().getLat(), p.getEndLocation().getLng());
                if( dStart <= 3 && dEnd <=3){
                    result.add(mapToDTO(p,trip, startAddress));
                }
            }

            // Chỉ nhập city, k có điểmdon điểm trả -> dùng user location
            else if (startAddress == null && userLocation != null) {
                double d = distance(userLocation.getLat(), userLocation.getLng(),
                        p.getStartLocation().getLat(), p.getStartLocation().getLng());
                suggestList.add(new ProductDistanceWrapper(p,trip,d));
            }
        }
        // Nếu không có kết quả từ startAddress ⇒ fallback userLocation
        if (!result.isEmpty()) {
            return result;
        }

        // fallback userLocation
        if (userLocation != null) {
            suggestList.sort(Comparator.comparingDouble(ProductDistanceWrapper::getDistance));
            return suggestList.stream()
                    .limit(10)
                    .map(e -> mapToDTO(e.getProduct(), e.getTrip(), userLocation))
                    .toList();
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductSearchDTO> findAll2(LocationDTO userLocation) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        // Lấy tất cả trip thỏa điều kiện
        List<Trip> trips = tripRepository
                .findByTravelDateAndStartTimeAfterAndStatus(today, now, "TRUE");

        List<ProductSearchDTO> result = new ArrayList<>();

        for (Trip trip : trips) {
            Product product = trip.getProduct();
            if (product == null) continue;

            // dùng hàm mapToDTO mới
            ProductSearchDTO dto = mapToDTO(product, trip, userLocation);

            // add vào list trả về
            result.add(dto);
        }

        return result;
    }

    @Override
    public List<ProductSearchDTO> findAllPagi(int page, int size, LocationDTO userLocation) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        Pageable pageable = PageRequest.of(page - 1, size);

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        Page<Trip> trips = tripRepository
                .findByTravelDateAndStartTimeAfterAndStatus(
                        today,
                        now,
                        "TRUE",
                        pageable
                );

        List<ProductSearchDTO> result = new ArrayList<>();

        for (Trip trip : trips) {
            Product product = trip.getProduct();
            if (product == null) continue;

            ProductSearchDTO dto = mapToDTO(product, trip, userLocation);
            result.add(dto);
        }

        return result;
    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductSearchDTO mapToDTO(Product product, Trip trip, LocationDTO locationDTO) {

        ProductSearchDTO dto = new ProductSearchDTO();

        // ================================
        // 1. PRODUCT INFO
        // ================================
        dto.setProductID(product.getProductID());
        dto.setLicense_plates(product.getLicense_plates());
        dto.setDescription(product.getDescription());
        dto.setPhone_number(product.getPhone_number());
        dto.setPhone_number2(product.getPhone_number2());
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

        // ================================
        // 2. MAP location → LocationDTO
        // ================================
        if (product.getStartLocation() != null) {
            dto.setStartLocation(new LocationDTO(
                    product.getStartLocation().getName(),
                    product.getStartLocation().getLat(),
                    product.getStartLocation().getLng()
            ));
        }

        if (product.getEndLocation() != null) {
            dto.setEndLocation(new LocationDTO(
                    product.getEndLocation().getName(),
                    product.getEndLocation().getLat(),
                    product.getEndLocation().getLng()
            ));
        }

        // ================================
        // 3. IMAGES → ImageDTO
        // ================================
        if (product.getImages() != null) {
            dto.setImageDTOS(
                    product.getImages()
                            .stream()
                            .map(img -> new ImageDTO(
                                    img.getImage_url()
                            ))
                            .toList()
            );
        }

        // ================================
        // 4. NOTICES → CreateNoticeDTO
        // ================================
        if (product.getNoticeList() != null) {
            dto.setNoticeDTOS(
                    product.getNoticeList()
                            .stream()
                            .map(n -> new CreateNoticeDTO(
                                    n.getProduct().getProductID(),
                                    n.getTitle(),
                                    n.getContent(),
                                    n.getStatus()
                            ))
                            .toList()
            );
        }

        // ================================
        // 5. TRIP INFO
        // ================================
        if (trip != null) {
            dto.setTrip_id(trip.getTripId());
            dto.setTravel_date(trip.getTravelDate());
            dto.setTrip_start_time(trip.getStartTime());
            dto.setTrip_end_time(trip.getEndTime());
            dto.setTrip_price(trip.getPrice());
            dto.setRemain_seat(trip.getRemainSeat());
            dto.setTrip_status(trip.getStatus());
        }

        // ================================
        // 6. Stop
        // ================================
        if (product.getStopList() != null) {
            dto.setStopDTOS(
                    product.getStopList()
                            .stream()
                            .map(n -> new StopDTO(
                                    new LocationDTO(
                                            n.getLocation().getName(),
                                            n.getLocation().getLat(),
                                            n.getLocation().getLng()
                                    ),
                                    n.getStopTime(),
                                    n.getType(),
                                    n.isDeleted()
                            ))
                            .toList()
            );
        }

        // Distance
        if( product.getStartLocation() != null){
            double dist = distance(product.getStartLocation().getLat(), product.getStartLocation().getLng(),
                    locationDTO.getLat(), locationDTO.getLng());
            dto.setDistance(dist);
        }
        return dto;
    }

    /**
     * Tính khoảng cách giữa 2 điểm theo kinh độ và vĩ độ
     * @param lat1 vĩ độ điểm 1 (degrees)
     * @param lon1 kinh độ điểm 1 (degrees)
     * @param lat2 vĩ độ điểm 2 (degrees)
     * @param lon2 kinh độ điểm 2 (degrees)
     * @return khoảng cách (km)
     */
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // Kiểm tra phạm vi hợp lệ
        if(lat1 < -90 || lat1 > 90 || lat2 < -90 || lat2 > 90) {
            throw new IllegalArgumentException("Latitude phải nằm trong [-90, 90]");
        }
        if(lon1 < -180 || lon1 > 180 || lon2 < -180 || lon2 > 180) {
            throw new IllegalArgumentException("Longitude phải nằm trong [-180, 180]");
        }

        final int R = 6371; // Bán kính Trái Đất (km)

        // Δlat và Δlon (radian)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // Convert lat1, lat2 sang radian
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);

        // Haversine formula
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c; // km

        return distance;
    }


}
