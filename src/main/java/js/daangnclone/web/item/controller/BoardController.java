package js.daangnclone.web.item.controller;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.domain.board.BoardForm;
import js.daangnclone.domain.category.Category;
import js.daangnclone.domain.category.CategoryRepository;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final MemberService memberService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/boards/new")
    public String createForm(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        memberService.validateCertifyLocation(principalUserDetails.getMember().getId());

        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("boardForm", new BoardForm());
        model.addAttribute("categoryList", categoryList);
        return "board/BoardForm";
    }

    @ExceptionHandler
    public String NotValidateCertifyLocationExceptionHandler(CustomException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("errorMsg", e.getErrorCode().getDetail());
        return "redirect:/";
    }
}
