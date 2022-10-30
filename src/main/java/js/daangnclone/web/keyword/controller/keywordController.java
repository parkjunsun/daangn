package js.daangnclone.web.keyword.controller;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.keyword.KeywordService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.keyword.dto.KeywordForm;
import js.daangnclone.web.keyword.dto.KeywordResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class keywordController {

    private final KeywordService keywordService;
    private final MemberService memberService;

    @GetMapping("/keyword/new")
    public String showKeywordForm(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);
        model.addAttribute("keywordForm", new KeywordForm());
        model.addAttribute("certifyYn", findMember.getCertifyYn());
        model.addAttribute("nickname", findMember.getNickname());

        List<KeywordResponse> keywordList = keywordService.inquireKeywordList(findMember);
        model.addAttribute("keywordList", keywordList);

        return "keyword/KeywordForm";
    }

    @PostMapping("/keyword/new")
    public String registerKeywordForm(@Valid @ModelAttribute KeywordForm keywordForm, BindingResult result,
                                      @AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {

        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);

        if (result.hasErrors()) {
            model.addAttribute("certifyYn", findMember.getCertifyYn());
            model.addAttribute("nickname", findMember.getNickname());
            return "keyword/KeywordForm";
        }

        keywordService.validateKeywordMaxCnt(findMember);
        keywordService.registerKeyword(keywordForm, findMember);

        return "redirect:/keyword/new";
    }

    @PostMapping("/keyword/{keywordId}/delete.do")
    @ResponseBody
    public void deleteKeyword(@PathVariable("keywordId") Long keywordId) {
        keywordService.deleteKeyword(keywordId);
    }


    @ExceptionHandler
    public String NotValidateKeywordMaxCntExceptionHandler(CustomException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMsg", e.getErrorCode().getDetail());
        return "redirect:/keyword/new";
    }

}
