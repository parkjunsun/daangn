package js.daangnclone.web.member.controller;

import js.daangnclone.exception.CustomException;
import js.daangnclone.cmn.Base64CryptoUtil;
import js.daangnclone.cmn.area.Area;
import js.daangnclone.cmn.area.AreaDto;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.mail.MailService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static js.daangnclone.exception.ErrorCode.EXPIRED_LINK_ADDRESS;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    private static final String ALERT_MESSAGE = "비밀번호를 초기화 하는 방법을 이메일 주소로 전송완료 했습니다. 가입한 적이 없는 이메일 주소나 올바르지 않은 이메일 주소를 입력하신 경우에는 메일을 받을 수 없습니다.";

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
//        model.addAttribute("nickname", findMember.getNickname());
//        model.addAttribute("certifyYn", findMember.getCertifyYn());
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

    @GetMapping("/forget")
    public String findAccountForm() {
        return "member/FindAccountForm";
    }



    @PostMapping("/forget/find.do")
    @ResponseBody
    public String sendEmail(@RequestParam String email) throws Exception {
        mailService.sendMail(email);
        return ALERT_MESSAGE;
    }

    @GetMapping("/password/reset/{encryptedData}")
    public String resetPasswordForm(@PathVariable String encryptedData, Model model) throws Exception {
        String[] data = Base64CryptoUtil.decrypt(encryptedData).split("\\|");
        long expiredTime = Long.parseLong(data[1]) + 300L;
        long now = Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        if (now > expiredTime) { //메일 보내고 5분 지났을 경우 에러 띄움
            throw new CustomException(EXPIRED_LINK_ADDRESS);
        }

        model.addAttribute("encryptedData", encryptedData);
        return "member/ResetPasswordForm";
    }

    @PostMapping("/password/reset/{encryptedData}")
    public String resetPassword(@PathVariable String encryptedData, @RequestParam String password, RedirectAttributes redirectAttributes) throws Exception {
        String username = Base64CryptoUtil.decrypt(encryptedData).split("\\|")[0];
        memberService.updateMemberPassword(username, password);
        redirectAttributes.addFlashAttribute("successMsg", "비밀번호 변경가 변경되었습니다.\n변경된 비밀번호로 로그인 해주세요.");
        return "redirect:/login";
    }

    @ExceptionHandler
    public String ExpiredLinkAddressExceptionHandler(CustomException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMsg", e.getErrorCode().getDetail());
        return "redirect:/forget";
    }

}
