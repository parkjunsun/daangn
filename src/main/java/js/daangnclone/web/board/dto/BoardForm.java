package js.daangnclone.web.board.dto;

import lombok.Data;

@Data
public class BoardForm {

    private String image;
    private String title;
    private Long category;
    private int price;
    private String content;
    private String detail;

}
