package js.daangnclone.web.chatNotification.controller;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.chat.ChatService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.chatNotification.dto.ChatListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatNotificationController {

    private final ChatService chatService;
    private final MemberService memberService;
    private final BoardService boardService;

    @GetMapping("/chatList")
    public String inquireChatList(Model model, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);

        List<ChatListResponse> chatList = chatService.findAllChatRoom(findMember);
        model.addAttribute("chatList", chatList);
//        model.addAttribute("certifyYn", findMember.getCertifyYn());
//        model.addAttribute("nickname", findMember.getNickname());

        return "chat/InquireChatList";
    }
}
