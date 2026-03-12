package js.daangnclone.web.member.controller;

import js.daangnclone.cmn.CurrentMember;
import js.daangnclone.cmn.area.Area;
import js.daangnclone.domain.member.Member;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.member.dto.AddressForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final MemberService memberService;

    @GetMapping("/address")
    public String showAddressForm(Model model, @CurrentMember Member currentMember) {
        Area findArea = currentMember.getArea();
        AddressForm addressForm = new AddressForm();
        addressForm.setCertifyYn(currentMember.getCertifyYn());
        addressForm.setState(findArea.getParent().getAreaCd());
        addressForm.setCity(findArea.getAreaCd());

        model.addAttribute("addressForm", addressForm);

        return "settings/UpdateAddressForm";
    }

    @PostMapping("/address")
    public String updateAddress(@CurrentMember Member currentMember, @ModelAttribute AddressForm addressForm, RedirectAttributes redirectAttributes) {
        memberService.updateMemberAddress(currentMember.getId(), addressForm);
        redirectAttributes.addFlashAttribute("successMsg", "주소 변경 성공!!");

        return "redirect:/boardList";
    }

}
