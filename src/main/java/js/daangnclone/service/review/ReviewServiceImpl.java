package js.daangnclone.service.review;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.review.Review;
import js.daangnclone.domain.review.ReviewRepository;
import js.daangnclone.domain.review.ReviewType;
import js.daangnclone.web.review.dto.ReviewForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public Review writeReview(ReviewForm reviewForm, Member sender, Member receiver, Board board, ReviewType reviewType) {
        String reviews = String.join("|", reviewForm.getReviews());

        Review newReview = Review.builder()
                .reviewType(reviewType)
                .sender(sender)
                .receiver(receiver)
                .board(board)
                .reviewScore(reviewForm.getReviewScore())
                .reviews(reviews)
                .content(reviewForm.getContent())
                .build();

        return reviewRepository.save(newReview);
    }
}
