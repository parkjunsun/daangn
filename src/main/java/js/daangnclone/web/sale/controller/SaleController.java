package js.daangnclone.web.sale.controller;

import js.daangnclone.domain.board.BoardStatus;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.sale.dto.SaleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SaleController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/saleList")
    public String inquireSaleList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);

        List<SaleResponse> saleOnList = boardService.inquireSaleList(findMember, BoardStatus.SALE_ON);

        long numberOfSaleOn = boardService.getCount(findMember, BoardStatus.SALE_ON);
        long numberOfSaleComp = boardService.getCount(findMember, BoardStatus.SALE_COMP);

        model.addAttribute("saleOnList", saleOnList);
        model.addAttribute("numberOfSaleOn", numberOfSaleOn);
        model.addAttribute("numberOfSaleComp", numberOfSaleComp);

        model.addAttribute("certifyYn", findMember.getCertifyYn());
        model.addAttribute("nickname", findMember.getNickname());

        return "sale/InquireSaleList";
    }


}
