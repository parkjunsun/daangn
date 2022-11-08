package js.daangnclone.web.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardForm {

    private Long boardId;
    private String image;
    private String title;
    private Long category;
    private int price;
    private String content;
    private String detail;

    @Builder
    public BoardForm (Long boardId, String image, String title, Long category, int price, String content, String detail) {
        this.boardId = boardId;
        this.image = image;
        this.title = title;
        this.category = category;
        this.price = price;
        this.content = content;
        this.detail = detail;
    }

}
