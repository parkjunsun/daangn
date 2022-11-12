package js.daangnclone.web.member.controller;

import js.daangnclone.cmn.area.Area;
import js.daangnclone.cmn.area.AreaDto;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.member.dto.MemberDetailsForm;
import js.daangnclone.web.member.dto.MemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String ShowMemberForm(Model model) {

        model.addAttribute("memberForm", new MemberForm());
        return "member/CreateMemberForm";
    }

    @PostMapping("/signup")
    public String createMember(@Valid @ModelAttribute("memberForm") MemberForm memberForm, BindingResult result, Model model) {

        if (memberForm.getState() == null || memberForm.getCity() == null) {
            result.reject("address", null, null);
        }

        if (result.hasErrors()) {
            return "member/CreateMemberForm";
        }

        memberService.save(memberForm);
        return "redirect:/";
    }

    @PostMapping("/signup/details")
    public String addAddress(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                             @ModelAttribute("memberDetailsForm") MemberDetailsForm detailsForm, BindingResult result, Model model) {

        if (detailsForm.getState() == null || detailsForm.getCity() == null) {
            result.reject("address", null, null);
        }

        if (result.hasErrors()) {
            return "member/AddAddressMemberForm";
        }

        Long memberId = principalUserDetails.getMember().getId();
        memberService.addDetails(memberId, detailsForm);

        return "redirect:/";
    }

    //ajax 데이터
    @PostMapping("/signup/getPsAreaCd.do")
    @ResponseBody
    public List<AreaDto> getPsAreaList(@RequestParam String pprAreaCd) {
        List<Area> areaList = Area.of(Long.parseLong(pprAreaCd)).children();
        return areaList.stream()
                .map(area -> AreaDto.builder()
                        .areaCd(area.getAreaCd())
                        .areaName(area.getAreaName())
                        .build())
                .collect(Collectors.toList());

    }

    //ajax 데이터
    @PostMapping("/signup/validate-username.do")
    @ResponseBody
    public String validateUsernameYn(@RequestParam String username) {
        String canUseYn = memberService.validateDuplicateUsername(username);
        return canUseYn;
    }

    //ajax 데이터
    @PostMapping("/signup/validate-nickname.do")
    @ResponseBody
    public String validateNicknameYn(@RequestParam String nickname) {
        String canUseYn = memberService.validateDuplicateNickname(nickname);
        return canUseYn;
    }

    @GetMapping("/certify")
    public String getCertifyMember(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long id = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(id);

        model.addAttribute("city", findMember.getArea().getAreaName());
        model.addAttribute("nickname", findMember.getNickname());
        model.addAttribute("certifyYn", findMember.getCertifyYn());
        return "member/CertifyMemberAddress";
    }

    @PostMapping("/certify")
    public String setCertifyMember(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, RedirectAttributes redirectAttributes) {
        memberService.updateMemberCertifyYn(principalUserDetails.getMember().getId());

        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);

//        Area findArea = areaRepository.findByAreaCd(findMember.getCity()).orElse(null);
        redirectAttributes.addFlashAttribute("successMsg", findMember.getArea().getAreaName() + " 동네 인증 성공!!");
        return "redirect:/boardList";
    }


    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "member/LoginForm";
    }

    @GetMapping("/logout")
    public String logout(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, HttpServletRequest request, HttpServletResponse response) {

        if (principalUserDetails != null) {
            new SecurityContextLogoutHandler().logout(request, response, (Authentication) principalUserDetails);
        }

        return "redirect:/login";
    }

}
