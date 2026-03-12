package js.daangnclone.web.keyword.controller;

import js.daangnclone.cmn.CurrentMember;
import js.daangnclone.exception.CustomException;
import js.daangnclone.domain.member.Member;
import js.daangnclone.service.keyword.KeywordService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.keyword.dto.KeywordForm;
import js.daangnclone.web.keyword.dto.KeywordResponse;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/keyword/new")
    public String showKeywordForm(@CurrentMember Member currentMember, Model model) {
        model.addAttribute("keywordForm", new KeywordForm());

        List<KeywordResponse> keywordList = keywordService.inquireKeywordList(currentMember);
        model.addAttribute("keywordList", keywordList);

        return "keyword/KeywordForm";
    }

    @PostMapping("/keyword/new")
    public String registerKeywordForm(@Valid @ModelAttribute KeywordForm keywordForm, BindingResult result,
                                      @CurrentMember Member currentMember, Model model) {
        if (result.hasErrors()) {
            return "keyword/KeywordForm";
        }

        keywordService.validateKeywordMaxCnt(currentMember);
        keywordService.registerKeyword(keywordForm, currentMember);

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
