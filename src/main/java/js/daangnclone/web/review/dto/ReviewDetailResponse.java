package js.daangnclone.web.review.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReviewDetailResponse {

    private Long id;
    private String senderName;
    private String boardTitle;
    private String reviewContent;
    private Map<String, String> reviews;

    @Builder
    public ReviewDetailResponse(Long id, String senderName, String boardTitle, String reviewContent, Map<String, String> reviews) {
        this.id = id;
        this.senderName = senderName;
        this.boardTitle = boardTitle;
        this.reviewContent = reviewContent;
        this.reviews = reviews;
    }
}
