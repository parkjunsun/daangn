package js.daangnclone.web.review.controller;

import js.daangnclone.exception.CustomException;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.review.ReviewScore;
import js.daangnclone.domain.review.ReviewType;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.service.review.ReviewService;
import js.daangnclone.web.board.dto.BoardSingleResponse;
import js.daangnclone.web.review.dto.ReviewDetailResponse;
import js.daangnclone.web.review.dto.ReviewForm;
import js.daangnclone.web.review.dto.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;
    private final BoardService boardService;

    //컨트롤러 호출할 때, model 에 자동으로 담김
    @ModelAttribute("reviews")
    public Map<String, String> reviews() {
        Map<String, String> reviews = new LinkedHashMap<>();
        reviews.put("01", "무료로 나눠주셨어요.");
        reviews.put("02", "상품상태가 설명한 것과 같아요.");
        reviews.put("03", "상품설명이 자세해요.");
        reviews.put("04", "좋은 상품을 저렴하게 판매해요.");
        reviews.put("05", "시간약속을 잘 지켜요.");
        reviews.put("06", "응답이 빨라요.");
        return reviews;
    }

    @ModelAttribute("reviewScores")
    public ReviewScore[] reviewScores() {
        return ReviewScore.values();
    }

    @GetMapping("/review/{boardId}/toPurchaser")
    public String createReviewFormToPurchaser(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);
        BoardSingleResponse findBoard = boardService.inquireBoard(boardId);

        model.addAttribute("board", findBoard);
        model.addAttribute("reviewForm", new ReviewForm());
//        model.addAttribute("certifyYn", findMember.getCertifyYn());
//        model.addAttribute("nickname", findMember.getNickname());
        return "review/CreateReviewFormToPurchaser";
    }

    @GetMapping("/review/{boardId}/toSeller")
    public String createReviewFormToSeller(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);
        BoardSingleResponse findBoard = boardService.inquireBoard(boardId);

        model.addAttribute("board", findBoard);
        model.addAttribute("reviewForm", new ReviewForm());
//        model.addAttribute("certifyYn", findMember.getCertifyYn());
//        model.addAttribute("nickname", findMember.getNickname());

        return "review/CreateReviewFormToSeller";
    }


    @PostMapping("/review/{boardId}/toPurchaser")
    public String sendReviewToPurchaser(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                                        @ModelAttribute ReviewForm reviewForm, RedirectAttributes redirectAttributes) {
        Long senderId = principalUserDetails.getMember().getId();
        Member sender = memberService.findMember(senderId); //sender
        Member receiver = memberService.findMember(reviewForm.getReceiverId()); //receiver
        Board findBoard = boardService.findBoard(boardId);

        reviewService.writeReview(reviewForm, sender, receiver, findBoard, ReviewType.SELLER_REVIEW);

        redirectAttributes.addFlashAttribute("successMsg", "후기 등록 성공!!");

        return "redirect:/boardList";
    }

    @PostMapping("/review/{boardId}/toSeller")
    public String sendReviewToSeller(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                                     @ModelAttribute ReviewForm reviewForm, RedirectAttributes redirectAttributes) {
        Long senderId = principalUserDetails.getMember().getId();
        Member sender = memberService.findMember(senderId); //sender
        Member receiver = memberService.findMember(reviewForm.getReceiverId()); //receiver
        Board findBoard = boardService.findBoard(boardId);

        reviewService.writeReview(reviewForm, sender, receiver, findBoard, ReviewType.PURCHASER_REVIEW);

        redirectAttributes.addFlashAttribute("successMsg", "후기 등록 성공!!");

        return "redirect:/boardList";

    }

    @GetMapping("/reviewList/all")
    public String inquireReviewList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(memberId);

        List<ReviewResponse> reviewList = reviewService.inquireAllReviewList(receiver);
        model.addAttribute("reviewList", reviewList);

        putCategorizeReviewCount(receiver, model);

//        model.addAttribute("certifyYn", receiver.getCertifyYn());
//        model.addAttribute("nickname", receiver.getNickname());

        return "review/InquireAllReviewList";
    }

    private void putCategorizeReviewCount(Member receiver, Model model) {
        long reviewAllCount = reviewService.getReviewAllCount(receiver);
        long reviewSellerCount = reviewService.getReviewTypeCount(receiver, ReviewType.SELLER_REVIEW);
        long reviewPurchaserCount = reviewService.getReviewTypeCount(receiver, ReviewType.PURCHASER_REVIEW);
        model.addAttribute("reviewAllCount", reviewAllCount);
        model.addAttribute("reviewSellerCount", reviewSellerCount);
        model.addAttribute("reviewPurchaserCount", reviewPurchaserCount);
    }

    @GetMapping("/reviewList/seller")
    public String inquireSellerReviewList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(memberId);

        List<ReviewResponse> reviewList = reviewService.inquireReviewList(receiver, ReviewType.SELLER_REVIEW);
        model.addAttribute("reviewList", reviewList);

        putCategorizeReviewCount(receiver, model);

//        model.addAttribute("certifyYn", receiver.getCertifyYn());
//        model.addAttribute("nickname", receiver.getNickname());

        return "review/InquireSellerReviewList";

    }


    @GetMapping("/reviewList/purchaser")
    public String inquirePurchaserReviewList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(memberId);

        List<ReviewResponse> reviewList = reviewService.inquireReviewList(receiver, ReviewType.PURCHASER_REVIEW);
        model.addAttribute("reviewList", reviewList);

        putCategorizeReviewCount(receiver, model);

//        model.addAttribute("certifyYn", receiver.getCertifyYn());
//        model.addAttribute("nickname", receiver.getNickname());

        return "review/InquirePurchaserReviewList";

    }

    @GetMapping("/reviewList/{reviewId}")
    public String inquireReview(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, @PathVariable Long reviewId, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member loginMember = memberService.findMember(memberId);

        reviewService.validateMyReview(loginMember, reviewId);

        ReviewDetailResponse review = reviewService.inquireReview(reviewId);
        model.addAttribute("review", review);

        return "review/InquireReview";
    }

    @ExceptionHandler
    public String NotValidateVerifiableReviewExceptionHandler(CustomException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMsg", e.getErrorCode().getDetail());
        return "redirect:/boardList";
    }
}
