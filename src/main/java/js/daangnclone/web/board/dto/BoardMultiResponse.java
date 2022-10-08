package js.daangnclone.web.board.dto;

import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.board.BoardStatus;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.web.comment.dto.CommentResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardMultiResponse {

    private Long id;
    private String title;
    private String image;
    private int price;
    private String content;
    private String detail;
    private String category;
    private String diffCreatedAt;
    private int view;
    private BoardStatus boardStatus;

    private String nickname;
    private String city;

    @Builder
    private BoardMultiResponse(Long id, String title, String image, int price, String content, String detail, String nickname, String category, String city, String diffCreatedAt, int view, BoardStatus boardStatus) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.content = content;
        this.detail = detail;
        this.nickname = nickname;
        this.category = category;
        this.city = city;
        this.diffCreatedAt = diffCreatedAt;
        this.view = view;
        this.boardStatus = boardStatus;
    }
}
