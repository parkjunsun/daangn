package js.daangnclone.domain.review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum ReviewScore {

    BAD(-10, "별로에요"),
    GOOD(10, "좋아요!"),
    EXCELLENT(20, "최고에요!");

    private final int score;
    private final String description;

    ReviewScore(int score, String description) {
        this.score = score;
        this.description = description;
    }

}
