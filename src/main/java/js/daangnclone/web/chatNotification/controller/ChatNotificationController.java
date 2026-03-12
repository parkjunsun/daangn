package js.daangnclone.web.chatNotification.controller;

import js.daangnclone.cmn.CurrentMember;
import js.daangnclone.domain.member.Member;
import js.daangnclone.service.chat.ChatService;
import js.daangnclone.web.chatNotification.dto.ChatListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatNotificationController {

    private final ChatService chatService;

    @GetMapping("/chatList")
    public String inquireChatList(Model model, @CurrentMember Member currentMember) {
        List<ChatListResponse> chatList = chatService.findAllChatRoom(currentMember);
        model.addAttribute("chatList", chatList);

        return "chat/InquireChatList";
    }
}
