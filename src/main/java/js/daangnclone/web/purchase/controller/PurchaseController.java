package js.daangnclone.web.purchase.controller;

import js.daangnclone.cmn.CurrentMember;
import js.daangnclone.domain.member.Member;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.purchase.dto.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseController {

    private final BoardService boardService;

    @GetMapping("/purchaseList")
    public String inquirePurchaseList(@CurrentMember Member currentMember, Model model) {
        List<PurchaseResponse> purchaseList = boardService.inquirePurchaseList(currentMember);

        model.addAttribute("purchaseList", purchaseList);

        return "purchase/InquirePurchaseList";
    }

}
