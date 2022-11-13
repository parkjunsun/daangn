package js.daangnclone.web.review.controller;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.review.ReviewScore;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.service.review.ReviewService;
import js.daangnclone.web.board.dto.BoardSingleResponse;
import js.daangnclone.web.review.dto.ReviewForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
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

    @GetMapping("/review/{boardId}/purchaser")
    public String createReviewForm(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);
        BoardSingleResponse findBoard = boardService.inquireBoard(boardId);

        model.addAttribute("board", findBoard);
        model.addAttribute("reviewForm", new ReviewForm());
        model.addAttribute("certifyYn", findMember.getCertifyYn());
        model.addAttribute("nickname", findMember.getNickname());
        return "review/CreateReviewForm";
    }
//
//    @PostMapping("/review/{boardId}/purchaser")
//    public String SendReview(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
//        Long memberId = principalUserDetails.getMember().getId();
//        Member findMember = memberService.findMember(memberId);
//        Board findBoard = boardService.findBoard(boardId);
//    }
}
