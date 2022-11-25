package js.daangnclone.service.review;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.Exception.ErrorCode;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.review.Review;
import js.daangnclone.domain.review.ReviewRepository;
import js.daangnclone.domain.review.ReviewType;
import js.daangnclone.domain.review.event.ReviewCreatedEvent;
import js.daangnclone.web.review.dto.ReviewDetailResponse;
import js.daangnclone.web.review.dto.ReviewForm;
import js.daangnclone.web.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static js.daangnclone.Exception.ErrorCode.DUPLICATE_REVIEW;
import static js.daangnclone.Exception.ErrorCode.REVIEW_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Review writeReview(ReviewForm reviewForm, Member sender, Member receiver, Board board, ReviewType reviewType) {
        String reviews = String.join("|", reviewForm.getReviews());

        Review reviewBuild = Review.builder()
                .reviewType(reviewType)
                .sender(sender)
                .receiver(receiver)
                .board(board)
                .reviewScore(reviewForm.getReviewScore())
                .reviews(reviews)
                .content(reviewForm.getContent())
                .build();

        receiver.calcReviewScore(reviewForm.getReviewScore());

        Review review = reviewRepository.save(reviewBuild);
        eventPublisher.publishEvent(new ReviewCreatedEvent(review));
        return review;
    }

    @Override
    public List<ReviewResponse> inquireAllReviewList(Member receiver) {
        List<Review> allReviewList = reviewRepository.findByReceiver(receiver);
        return allReviewList.stream()
                .map(review -> ReviewResponse.builder()
                        .id(review.getId())
                        .senderName(review.getSender().getNickname())
                        .boardTitle(review.getBoard().getTitle())
                        .boardImage(review.getBoard().getImage())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> inquireReviewList(Member receiver, ReviewType reviewType) {
        List<Review> reviewList = reviewRepository.findByReceiverAndReviewType(receiver, reviewType);
        return reviewList.stream()
                .map(review -> ReviewResponse.builder()
                        .id(review.getId())
                        .senderName(review.getSender().getNickname())
                        .boardTitle(review.getBoard().getTitle())
                        .boardImage(review.getBoard().getImage())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDetailResponse inquireReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        LinkedHashMap<String, String> reviews = new LinkedHashMap<>();
        String[] reviewValues = review.getReviews().split("\\|");
        for (String reviewValue : reviewValues) {
            switch (reviewValue) {
                case "01":
                    reviews.put("01", "무료로 나눠주셨어요.");
                    break;
                case "02":
                    reviews.put("02", "상품상태가 설명한 것과 같아요.");
                    break;
                case "03":
                    reviews.put("03", "상품설명이 자세해요.");
                    break;
                case "04":
                    reviews.put("04", "좋은 상품을 저렴하게 판매해요.");
                    break;
                case "05":
                    reviews.put("05", "시간약속을 잘 지켜요.");
                    break;
                case "06":
                    reviews.put("06", "응답이 빨라요.");
                    break;
            }

        }

        return ReviewDetailResponse.builder()
                .id(review.getId())
                .senderName(review.getSender().getNickname())
                .boardTitle(review.getBoard().getTitle())
                .reviewContent(review.getContent())
                .reviews(reviews)
                .build();
    }

    @Override
    public long getReviewAllCount(Member receiver) {
        return reviewRepository.countByReceiver(receiver);
    }

    @Override
    public long getReviewTypeCount(Member receiver, ReviewType reviewType) {
       return reviewRepository.countByReceiverAndReviewType(receiver, reviewType);
    }

    @Override
    public void validateDuplicateReview(Member sender, Board board) {
        boolean isDuplicateReview = reviewRepository.existsBySenderAndBoard(sender, board);

        if (isDuplicateReview) {
            throw new CustomException(DUPLICATE_REVIEW);
        }
    }

}
