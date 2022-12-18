package js.daangnclone.service.review;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.review.Review;
import js.daangnclone.domain.review.ReviewType;
import js.daangnclone.web.review.dto.ReviewDetailResponse;
import js.daangnclone.web.review.dto.ReviewForm;
import js.daangnclone.web.review.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    Review writeReview(ReviewForm reviewForm, Member sender, Member receiver, Board board, ReviewType reviewType);
    List<ReviewResponse> inquireAllReviewList(Member receiver);
    List<ReviewResponse> inquireReviewList(Member receiver, ReviewType reviewType);
    ReviewDetailResponse inquireReview(Long reviewId);
    long getReviewAllCount(Member receiver);
    long getReviewTypeCount(Member receiver, ReviewType reviewType);
    void validateDuplicateReview(Member sender, Board board);
    void validateMyReview(Member member, Long reviewId);
}
