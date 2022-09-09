package js.daangnclone.web.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BoardResponse {

    private Long id;
    private String title;
    private String image;
    private int price;
    private String content;
    private String detail;
    private String category;
    private String diffUpdatedAt;
    private int view;

    private String nickname;
    private String city;

    @Builder
    private BoardResponse(Long id, String title, String image, int price, String content, String detail, String nickname, String category, String city, String diffUpdatedAt, int view) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.content = content;
        this.detail = detail;
        this.nickname = nickname;
        this.category = category;
        this.city = city;
        this.diffUpdatedAt = diffUpdatedAt;
        this.view = view;
    }

}
