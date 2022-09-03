package js.daangnclone.web.item.controller;

import js.daangnclone.Exception.CustomException;
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

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final MemberService memberService;

    @GetMapping("/items/new")
    public String createForm(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {

        memberService.validateCertifyLocation(principalUserDetails.getMember().getId());
        return "item/itemForm";
    }

    @ExceptionHandler
    public String NotValidateCertifyLocationExceptionHandler(CustomException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("errorMsg", e.getErrorCode().getDetail());
        return "redirect:/";
    }
}
