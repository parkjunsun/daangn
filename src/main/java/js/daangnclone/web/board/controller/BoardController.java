package js.daangnclone.web.board.controller;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.domain.area.AreaRepository;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.category.Category;
import js.daangnclone.domain.category.CategoryRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.attention.AttentionService;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.board.dto.BoardForm;
import js.daangnclone.web.board.dto.BoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final MemberService memberService;
    private final CategoryRepository categoryRepository;
    private final BoardService boardService;
    private final AttentionService attentionService;

    @GetMapping("/")
    public String inquireBoardList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        List<BoardResponse> boardResponsesList = boardService.inquireAllBoardList();
        model.addAttribute("boardList", boardResponsesList);
        return "board/InquireBoardList";
    }

    @GetMapping("/board/new")
    public String createBoardForm(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        memberService.validateCertifyLocation(principalUserDetails.getMember().getId());

        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("boardForm", new BoardForm());
        model.addAttribute("categoryList", categoryList);
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
        boardService.updateView(boardId);
        BoardResponse boardResponse = boardService.inquireBoard(boardId);

        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);
        Board findBoard = boardService.findBoard(boardId);

        String attentionInpYn = attentionService.InpAttentionYn(findMember, findBoard);
        long cnt = attentionService.countAttentionInBoard(findBoard);

        model.addAttribute("board", boardResponse);
        model.addAttribute("attentionInpYn", attentionInpYn);
        model.addAttribute("cntAttention", cnt);
        return "board/InquireBoard";
    }


    @ExceptionHandler
    public String NotValidateCertifyLocationExceptionHandler(CustomException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMsg", e.getErrorCode().getDetail());
        return "redirect:/";
    }
}
