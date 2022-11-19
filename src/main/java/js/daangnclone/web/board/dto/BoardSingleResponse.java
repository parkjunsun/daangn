package js.daangnclone.web.board.dto;

import js.daangnclone.domain.board.BoardStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class BoardSingleResponse {

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

    private Long memberId;
    private String nickname;
    private String city;

    private Long purchaserId;
    private String purchaserNickname;

//    private List<CommentResponse> commentList;



    @Builder
    private BoardSingleResponse(Long id, String title, String image, int price, String content, String detail,
                                Long memberId, String nickname, String category, String city, String diffCreatedAt,
                                int view, BoardStatus boardStatus, Long purchaserId, String purchaserNickname) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.content = content;
        this.detail = detail;
        this.memberId = memberId;
        this.nickname = nickname;
        this.category = category;
        this.city = city;
        this.diffCreatedAt = diffCreatedAt;
        this.view = view;
        this.boardStatus = boardStatus;
        this.purchaserId = purchaserId;
        this.purchaserNickname = purchaserNickname;
//        this.commentList = commentList.stream()
//                .map(comment -> CommentResponse.builder()
//                        .id(comment.getId())
//                        .content(comment.getContent())
//                        .diffCreatedAt(DateUtil.diffDate(comment.getCreatedAt()))
//                        .nickname(comment.getMember().getNickname())
//                        .city(comment.getMember().getArea().getAreaName())
//                        .provider(comment.getMember().getProvider())
//                        .likesList(comment.getLikeList())
//                        .build())
//                .collect(Collectors.toList());
    }

}
