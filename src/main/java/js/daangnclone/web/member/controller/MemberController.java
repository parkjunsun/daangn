package js.daangnclone.web.member.controller;

import js.daangnclone.domain.area.Area;
import js.daangnclone.domain.area.AreaRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.member.dto.MemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import static js.daangnclone.cmn.CmnCons.KOREA_CODE;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AreaRepository areaRepository;

    @GetMapping("/signup")
    public String ShowMemberForm(Model model) {

        List<Area> pprAreaList = areaRepository.findAreaByPprAreaCd(KOREA_CODE);

        model.addAttribute("memberForm", new MemberForm());
        model.addAttribute("pprAreaList", pprAreaList);

        return "member/CreateMemberForm";
    }

    @PostMapping("/signup")
    public String createMember(@Valid @ModelAttribute("memberForm") MemberForm memberForm, BindingResult result, Model model) {

        if (memberForm.getState() == null || memberForm.getCity() == null) {
            result.reject("address", null, null);
        }

        if (result.hasErrors()) {
            List<Area> pprAreaList = areaRepository.findAreaByPprAreaCd(KOREA_CODE);
            model.addAttribute("pprAreaList", pprAreaList);
            return "member/CreateMemberForm";
        }

        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        memberService.save(memberForm.toEntity());
        return "redirect:/";
    }

    //ajax 데이터
    @PostMapping("/signup/getPsAreaCd.do")
    @ResponseBody
    public List<Area> getPsAreaList(@RequestParam Long pprAreaCd) {
        List<Area> areaList = areaRepository.findAreaByPprAreaCd(pprAreaCd);
        return areaList;
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

        Area area = areaRepository.findByAreaCd(findMember.getCity()).orElse(null);
        model.addAttribute("city", area.getAreaName());
        return "member/CertifyMemberAddress";
    }

    @PostMapping("/certify")
    public String setCertifyMember(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, RedirectAttributes redirectAttributes) {
        memberService.updateMemberCertifyYn(principalUserDetails.getMember().getId());
        Area findArea = areaRepository.findByAreaCd(principalUserDetails.getMember().getCity()).orElse(null);
        redirectAttributes.addFlashAttribute("successMsg", findArea.getAreaName() + " 동네 인증 성공!!");
        return "redirect:/";
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
