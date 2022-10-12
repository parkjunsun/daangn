package js.daangnclone.web.chat.controller;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.chat.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class ChatUIController {

    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/board/{boardId}/chat")
    public String showChat(@PathVariable Long boardId, Model model, @RequestParam(value = "roomNum") String roomNum,
                           @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {

        Long senderId = principalUserDetails.getMember().getId();

        Member sender = memberService.findMember(senderId);
        Board board = boardService.findBoard(boardId);

        ChatResponse chatInfo = ChatResponse.builder()
                .senderId(sender.getId())
                .senderName(sender.getNickname())
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .boardImage(board.getImage())
                .boardPrice(board.getPrice())
                .receiverId(board.getMember().getId())
                .receiverName(board.getMember().getNickname())
                .roomNum(roomNum)
                .build();

        model.addAttribute("chatInfo", chatInfo);
        model.addAttribute("certifyYn", sender.getCertifyYn());

        return "chat/chat";
    }
}
