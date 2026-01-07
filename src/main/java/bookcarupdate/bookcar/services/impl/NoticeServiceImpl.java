package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.dto.CreateNoticeDTO;
import bookcarupdate.bookcar.dto.NoticeGetDTO;
import bookcarupdate.bookcar.models.Notice;
import bookcarupdate.bookcar.models.Product;
import bookcarupdate.bookcar.models.Store;
import bookcarupdate.bookcar.repositories.NoticeRepository;
import bookcarupdate.bookcar.repositories.ProductRepository;
import bookcarupdate.bookcar.services.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final ProductRepository productRepository;

    @Override
    public NoticeGetDTO getNotice(Long id) {
        return noticeRepository.getNoticeByNoticeID(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public List<NoticeGetDTO> getAllNotices(Long storeId) {
        return noticeRepository.getAllNotices(storeId);
    }

    @Override
    public Notice createNotice(CreateNoticeDTO dto) {
        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("ID sản phẩm không đúng!"));
        notice.setProduct(product);
        notice.setLastUpdate(new Date());
        notice.setStatus(dto.getStatus());

        Store store = product.getStore();
        notice.setStoreName(store.getStoreName());
        return noticeRepository.save(notice);
    }

    @Override
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    @Override
    public Notice updateNotice(Long id, CreateNoticeDTO createNoticeDTO) {
        Notice notice = noticeRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found"));
        notice.setProduct(productRepository.findById(createNoticeDTO.getProductId()).orElseThrow(()-> new RuntimeException("Not found")));
        notice.setTitle(createNoticeDTO.getTitle());
        notice.setContent(createNoticeDTO.getContent());
        notice.setStatus(createNoticeDTO.getStatus());
        notice.setLastUpdate(new Date());
        return noticeRepository.save(notice);
    }
}
