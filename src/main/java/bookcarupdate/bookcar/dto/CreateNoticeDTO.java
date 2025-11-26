package bookcarupdate.bookcar.dto;

import lombok.Data;

@Data
public class CreateNoticeDTO {
    private Long productId;
    private String title;
    private String content;
    private String status;
}
