package js.daangnclone.web.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InquireBoardDto {

    private String title;
    private String image;
    private Long category;
    private int price;
    private String content;
    private String detail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
