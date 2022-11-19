package js.daangnclone.service.review;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.review.Review;
import js.daangnclone.domain.review.ReviewType;
import js.daangnclone.web.review.dto.ReviewForm;

public interface ReviewService {

    Review writeReview(ReviewForm reviewForm, Member sender, Member receiver, Board board, ReviewType reviewType);
}
