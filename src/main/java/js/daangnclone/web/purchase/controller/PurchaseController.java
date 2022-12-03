package js.daangnclone.web.purchase.controller;

import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.purchase.dto.PurchaseResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/purchaseList")
    public String inquirePurchaseList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);

        List<PurchaseResponse> purchaseList = boardService.inquirePurchaseList(findMember);

        model.addAttribute("purchaseList", purchaseList);
//        model.addAttribute("certifyYn", findMember.getCertifyYn());
//        model.addAttribute("nickname", findMember.getNickname());

        return "purchase/InquirePurchaseList";
    }

}
