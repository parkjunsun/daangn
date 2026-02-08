package js.daangnclone.web.member.controller;

import js.daangnclone.cmn.CurrentMember;
import js.daangnclone.domain.member.Member;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.member.dto.ProfileForm;
import js.daangnclone.web.member.dto.ProfileResponse;
import lombok.RequiredArgsConstructor;
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
    public String showProfileForm(Model model, @CurrentMember Member currentMember) {
        ProfileForm profileForm = new ProfileForm();
        profileForm.setNickname(currentMember.getNickname());
        profileForm.setEmail(currentMember.getEmail());

        ProfileResponse profileInfo = memberService.inquireProfile(currentMember.getId());

        model.addAttribute("profileInfo", profileInfo);
        model.addAttribute("profileForm", profileForm);

        return "settings/UpdateProfileForm";
    }

    @PostMapping("/profile")
    public String updateProfile(@CurrentMember Member currentMember, @ModelAttribute ProfileForm profileForm) {
        memberService.updateMemberProfile(currentMember.getId(), profileForm);

        return "redirect:/profile";
    }
}
