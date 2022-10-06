package js.daangnclone.web.member.controller;

import js.daangnclone.cmn.Area;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.member.dto.AddressForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final MemberService memberService;

    @GetMapping("/address")
    public String showAddressForm(Model model, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);

        Area findArea = findMember.getArea();
        AddressForm addressForm = new AddressForm();
        addressForm.setCertifyYn(findMember.getCertifyYn());
        addressForm.setState(findArea.getParent().getAreaCd());
        addressForm.setCity(findArea.getAreaCd());

        model.addAttribute("addressForm", addressForm);
        model.addAttribute("provider", findMember.getProvider());
        model.addAttribute("nickname", findMember.getNickname());
        model.addAttribute("certifyYn", findMember.getCertifyYn());

        return "settings/UpdateAddressForm";
    }

    @PostMapping("/address")
    public String updateAddress(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, @ModelAttribute AddressForm addressForm) {
        Long memberId = principalUserDetails.getMember().getId();
        memberService.updateMemberAddress(memberId, addressForm);

        return "redirect:/address";
    }

}
