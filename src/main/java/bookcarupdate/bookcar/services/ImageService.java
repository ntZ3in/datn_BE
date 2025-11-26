package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.dto.ImageDTO;

import java.util.List;

public interface ImageService {
    List<ImageDTO> getImagesByProductId(Long productId);
}
