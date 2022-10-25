package js.daangnclone.web.sale.controller;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.board.BoardStatus;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.chat.ChatService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.board.dto.BoardSingleResponse;
import js.daangnclone.web.sale.dto.SaleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SaleController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final ChatService chatService;

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

    @PostMapping("/saleList/{boardId}")
    public String updateSaleStatus(@PathVariable("boardId") Long boardId, @RequestParam("saleComp") String code) {
        boardService.updateBoardStatus(boardId, BoardStatus.of(code));
        return "redirect:/saleList/" + boardId + "/purchaser";
    }

    @GetMapping("/saleList/{boardId}/purchaser")
    public String setPurchaseForm(@PathVariable("boardId") Long boardId, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);
        SaleResponse sale = boardService.inquireSale(boardId);

        model.addAttribute("sale", sale);
        model.addAttribute("certifyYn", findMember.getCertifyYn());
        model.addAttribute("nickname", findMember.getNickname());
        model.addAttribute("successMsg", "판매 완료되었어요.");
        return "sale/AddPurchaserForm";
    }


}
