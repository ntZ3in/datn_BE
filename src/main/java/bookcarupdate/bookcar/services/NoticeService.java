package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.dto.CreateNoticeDTO;
import bookcarupdate.bookcar.dto.NoticeGetDTO;
import bookcarupdate.bookcar.models.Notice;

import java.util.List;

public interface NoticeService {
    public Notice createNotice(CreateNoticeDTO createNoticeDTO);
    public List<NoticeGetDTO> getAllNotices(Long storeId);
    public NoticeGetDTO getNotice(Long id);
    public void deleteNotice(Long id);
    public Notice updateNotice(Long id, CreateNoticeDTO createNoticeDTO);
}
