package bookcarupdate.bookcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeGetDTO {
    private Long noticeId;
    private Long productId;
    private String productName;
    private LocalTime start_time;
    private String license_plates;
    private String title;
    private String content;
    private Date created_at;
    private Date last_update;
    private String status;
}
