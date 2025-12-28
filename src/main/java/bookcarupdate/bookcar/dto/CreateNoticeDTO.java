package bookcarupdate.bookcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateNoticeDTO {
    private Long productId;
    private String title;
    private String content;
    private String status;
}
