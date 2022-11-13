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
    private ReviewScore reviewScore;
    private List<String> reviews;
    private String msg;

    @Builder
    public ReviewForm(Long id, String msg){
        this.id = id;
        this.msg = msg;
    }

}
