package js.daangnclone.web.sale.controller;

import js.daangnclone.cmn.CurrentMember;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.board.BoardStatus;
import js.daangnclone.domain.member.Member;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.chat.ChatService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.sale.dto.PurchaserResponse;
import js.daangnclone.web.sale.dto.SaleResponse;
import lombok.RequiredArgsConstructor;
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
    private final ChatService chatService;
    private final MemberService memberService;

    @GetMapping("/saleList/saleOn")
    public String inquireSaleOnList(@CurrentMember Member currentMember, Model model) {
        List<SaleResponse> saleOnList = boardService.inquireSaleList(currentMember, BoardStatus.SALE_ON);

        long numberOfSaleOn = boardService.getCount(currentMember, BoardStatus.SALE_ON);
        long numberOfSaleComp = boardService.getCount(currentMember, BoardStatus.SALE_COMP);

        model.addAttribute("saleOnList", saleOnList);
        model.addAttribute("numberOfSaleOn", numberOfSaleOn);
        model.addAttribute("numberOfSaleComp", numberOfSaleComp);

        return "sale/InquireSaleOnList";
    }

    @GetMapping("/saleList/saleComp")
    public String inquireSaleCompList(@CurrentMember Member currentMember, Model model) {
        List<SaleResponse> saleCompList = boardService.inquireSaleList(currentMember, BoardStatus.SALE_COMP);

        long numberOfSaleOn = boardService.getCount(currentMember, BoardStatus.SALE_ON);
        long numberOfSaleComp = boardService.getCount(currentMember, BoardStatus.SALE_COMP);

        model.addAttribute("saleCompList", saleCompList);
        model.addAttribute("numberOfSaleOn", numberOfSaleOn);
        model.addAttribute("numberOfSaleComp", numberOfSaleComp);


        return "sale/InquireSaleCompList";
    }

    @PostMapping("/saleList/{boardId}")
    public String updateSaleStatus(@PathVariable("boardId") Long boardId, @RequestParam("saleComp") String code, RedirectAttributes redirectAttributes) {

        Board findBoard = boardService.findBoard(boardId);
        if (!findBoard.getBoardStatus().getKey().equals("02")) {
            redirectAttributes.addFlashAttribute("successMsg", "판매완료 되었어요");
        }
        boardService.updateBoardStatus(boardId, BoardStatus.of(code));
        return "redirect:/saleList/" + boardId + "/purchaser";
    }

    //거래자 선택 화면 controller
    @GetMapping("/saleList/{boardId}/purchaser")
    public String PurchaserForm(@PathVariable("boardId") Long boardId, @CurrentMember Member currentMember, Model model) {
        Board findBoard = boardService.findBoard(boardId);
        SaleResponse sale = boardService.inquireSale(boardId);
        List<PurchaserResponse> chatList = chatService.findChatList(findBoard);

        model.addAttribute("sale", sale);
        model.addAttribute("chatList", chatList);


        return "sale/AddPurchaserForm";
    }

    @PostMapping("/saleList/{boardId}/purchaser")
    public String setPurchaser(@PathVariable("boardId") Long boardId, @RequestParam(name = "purchaserId") Long purchaserId) {
        Member purchaser = memberService.findMember(purchaserId);
        boardService.updatePurchaser(boardId, purchaser);
        return "redirect:/saleList/saleComp";
    }


}
