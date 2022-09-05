package js.daangnclone.web.board.controller;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.category.Category;
import js.daangnclone.domain.category.CategoryRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.board.dto.BoardForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final MemberService memberService;
    private final CategoryRepository categoryRepository;
    private final BoardService boardService;

    @GetMapping("/board/new")
    public String createBoardForm(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        memberService.validateCertifyLocation(principalUserDetails.getMember().getId());

        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("boardForm", new BoardForm());
        model.addAttribute("categoryList", categoryList);
        return "board/BoardForm";
    }

    @PostMapping("/board/new")
    public String createBoard(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, @ModelAttribute BoardForm boardForm) {

        Member member = memberService.findMember(principalUserDetails.getMember().getId());
        Board board = new Board();
        board.setTitle(boardForm.getTitle());
        board.setCategory(boardForm.getCategory());
        board.setContent(boardForm.getContent());
        board.setDetail(boardForm.getDetail());
        board.setPrice(boardForm.getPrice());
        board.setMember(member);

        boardService.registerItem(board);
        return "redirect:/";
    }

    @ExceptionHandler
    public String NotValidateCertifyLocationExceptionHandler(CustomException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMsg", e.getErrorCode().getDetail());
        return "redirect:/";
    }
}
