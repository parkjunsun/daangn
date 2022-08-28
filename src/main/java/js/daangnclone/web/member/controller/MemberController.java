package js.daangnclone.web.member.controller;

import js.daangnclone.domain.area.Area;
import js.daangnclone.domain.area.AreaRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.MemberService;
import js.daangnclone.web.member.dto.CreateMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        model.addAttribute("memberForm", new CreateMemberDto());
        model.addAttribute("pprAreaList", pprAreaList);

        return "member/CreateMemberForm";
    }

    @PostMapping("/signup")
    public String createMember(@ModelAttribute("memberForm") CreateMemberDto createMemberDto) {
        createMemberDto.setPassword(passwordEncoder.encode(createMemberDto.getPassword()));
        memberService.save(createMemberDto.toEntity());
        return "redirect:/";
    }

    //ajax 데이터
    @PostMapping("/signup/getPsAreaCd.do")
    @ResponseBody
    public List<Area> getPsAreaList(@RequestParam Long pprAreaCd) {
        List<Area> areaList = areaRepository.findAreaByPprAreaCd(pprAreaCd);
        return areaList;
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
    public String setCertifyMember(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        memberService.updateMemberCertifyYn(principalUserDetails.getMember().getId());
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
