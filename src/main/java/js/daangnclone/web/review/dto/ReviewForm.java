package js.daangnclone.web.review.dto;

import js.daangnclone.domain.review.ReviewScore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewForm {

    private Long id;
    private int reviewScore;
    private List<String> reviews;
    private String content;
    private Long receiverId;

    @Builder
    public ReviewForm(Long id, int reviewScore, List<String> reviews, String content, Long receiverId){
        this.id = id;
        this.reviewScore = reviewScore;
        this.reviews = reviews;
        this.content = content;
        this.receiverId = receiverId;
    }

}
