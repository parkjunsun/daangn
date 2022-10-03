package js.daangnclone.web.member.controller;

import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.member.dto.ProfileForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public String showProfileForm(Model model, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {

        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);

        ProfileForm profileForm = new ProfileForm();
        profileForm.setNickname(findMember.getNickname());
        profileForm.setEmail(findMember.getEmail());

        model.addAttribute("profileForm", profileForm);
        model.addAttribute("provider", findMember.getProvider());
        model.addAttribute("nickname", findMember.getNickname());
        return "settings/UpdateProfileForm";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, @ModelAttribute ProfileForm profileForm) {
        Long memberId = principalUserDetails.getMember().getId();
        memberService.updateMemberProfile(memberId, profileForm);

        return "redirect:/profile";
    }
}
