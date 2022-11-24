package js.daangnclone.domain.review.event;

import js.daangnclone.domain.review.Review;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 후기 생성시 발생시킬 이벤트
 */

@Getter
public class ReviewCreatedEvent {

    private final Review review;

    public ReviewCreatedEvent(Review review) {
        this.review = review;
    }
}
