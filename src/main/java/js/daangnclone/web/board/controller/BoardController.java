package js.daangnclone.web.board.controller;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.cmn.Category;
import js.daangnclone.cmn.CategoryDto;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.board.BoardStatus;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.attention.AttentionService;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.comment.CommentService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.board.dto.BoardForm;
import js.daangnclone.web.board.dto.BoardMultiResponse;
import js.daangnclone.web.board.dto.BoardSingleResponse;
import js.daangnclone.web.comment.dto.CommentResponse;
import js.daangnclone.web.member.dto.MemberDetailsForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final AttentionService attentionService;
    private final CommentService commentService;

    @GetMapping("/")
    public String inquireBoardList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {

        if (principalUserDetails != null) {
            Long memberId = principalUserDetails.getMember().getId();
            Member findMember = memberService.findMember(memberId);
            model.addAttribute("certifyYn", findMember.getCertifyYn());

            if (findMember.getArea() == null) {
                model.addAttribute("memberDetailsForm", new MemberDetailsForm());
                return "member/AddDetailsMemberForm";
            }

            model.addAttribute("provider", findMember.getProvider());
            model.addAttribute("nickname", findMember.getNickname());
        }

        List<BoardMultiResponse> boardResponsesList = boardService.inquireAllBoardList();
        model.addAttribute("boardList", boardResponsesList);
        return "board/InquireBoardList";
    }

    @GetMapping("/board/new")
    public String createBoardForm(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);
        memberService.validateCertifyLocation(memberId);

//        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> categoryList = Arrays.stream(Category.values())
                .map(category -> CategoryDto.builder()
                        .categoryCd(category.getCategoryCd())
                        .categoryName(category.getCategoryName())
                        .build())
                .collect(Collectors.toList());

        model.addAttribute("boardForm", new BoardForm());
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("provider", findMember.getProvider());
        model.addAttribute("nickname", findMember.getNickname());
        model.addAttribute("certifyYn", findMember.getCertifyYn());
        return "board/BoardForm";
    }

    @PostMapping("/board/new")
    public String createBoard(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, @ModelAttribute BoardForm boardForm, RedirectAttributes redirectAttributes) {

        Member member = memberService.findMember(principalUserDetails.getMember().getId());
        boardService.registerItem(boardForm, member);
        redirectAttributes.addFlashAttribute("successMsg", "상품 등록 성공!!");
        return "redirect:/";
    }

    @GetMapping("/board/{id}")
    public String inquireBoard(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, @PathVariable("id") Long boardId, Model model) {
        Long memberId = principalUserDetails.getMember().getId();

        boardService.updateView(boardId);
        BoardSingleResponse boardResponse = boardService.inquireBoard(boardId);
        List<CommentResponse> commentResponseList = commentService.inquireCommentList(boardId, memberId);

        Member findMember = memberService.findMember(memberId);
        Board findBoard = boardService.findBoard(boardId);

        String attentionInpYn = attentionService.getInpAttentionYn(findMember, findBoard);
        long cnt = attentionService.countAttentionInBoard(findBoard);

        model.addAttribute("board", boardResponse);
        model.addAttribute("commentList", commentResponseList);
        model.addAttribute("attentionInpYn", attentionInpYn);
        model.addAttribute("attentionCnt", cnt);
        model.addAttribute("provider", findMember.getProvider());
        model.addAttribute("nickname", findMember.getNickname());
        model.addAttribute("certifyYn", findMember.getCertifyYn());
        model.addAttribute("memberId", findMember.getId());
        return "board/InquireBoard";
    }

    @PostMapping("/board/{id}/change.do")
    @ResponseBody
    public Message changeBoardStatus(@PathVariable("id") Long boardId, @RequestParam String boardStatusCd) {
        Board findBoard = boardService.updateBoardStatus(boardId, BoardStatus.of(boardStatusCd));
        Message message = new Message();
        message.setTitle("Success");
        message.setMsg("상품의 상태가 \"" + findBoard.getBoardStatus().getValue() + "\"" + "로 바뀌었습니다.");

        return message;
    }

    static class Message {
        private String title;
        private String msg;

        public String getTitle() {return title;}
        public String getMsg() { return msg; }
        public void setTitle(String title) {this.title = title;}
        public void setMsg(String msg) {this.msg = msg;}

    }

    @ExceptionHandler
    public String NotValidateCertifyLocationExceptionHandler(CustomException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMsg", e.getErrorCode().getDetail());
        return "redirect:/";
    }
}
