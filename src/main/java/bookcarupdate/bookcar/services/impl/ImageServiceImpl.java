package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.dto.ImageDTO;
import bookcarupdate.bookcar.repositories.ImageRepository;
import bookcarupdate.bookcar.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    @Override
    public List<ImageDTO> getImagesByProductId(Long productId) {
        return imageRepository.findAllByProductId(productId)
                .orElseThrow(()-> new RuntimeException("Not found"));
    }
}
