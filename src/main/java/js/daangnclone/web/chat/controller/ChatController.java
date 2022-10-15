package js.daangnclone.web.chat.controller;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.chat.ChatService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.chat.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final ChatService chatService;

    @GetMapping("/board/{boardId}/chat")
    public String showChat(@PathVariable Long boardId, Model model, @RequestParam(value = "roomNum") String roomNum,
                           @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {

        Long memberId = principalUserDetails.getMember().getId();

        Member findMember = memberService.findMember(memberId);
        Board findBoard = boardService.findBoard(boardId);

        Optional<Chat> findChatRoom = chatService.findChatRoom(roomNum);

        Long senderId = null;
        String senderName = null;
        Long receiverId = null;
        String receiverName = null;


        //채팅하기로 채팅방이 생성되있지 않은 경우에는 채팅방을 만들고 메세지 sender를 채팅건 사람, 메세지 receiver를 상품 판매자로 간주한다.
        if (findChatRoom.isEmpty()) {
            Chat chat = Chat.builder()
                    .roomNum(roomNum)
                    .board(findBoard)
                    .seller(findBoard.getMember())
                    .buyer(findMember)
                    .build();

            Chat newChat = chatService.createChatRoom(chat);
            senderId = newChat.getBuyer().getId();
            senderName = newChat.getBuyer().getNickname();
            receiverId = newChat.getSeller().getId();
            receiverName = newChat.getSeller().getNickname();

        }

        // 이미 채팅방이 생성된 경우에는 두가지 경우로 분기한다.
        // 1. 판매자가 채팅방에 들어온 경우 판매자가 메세지 sender, 구매자가 메세지 receiver가 된다.
        // 2. 구매자가 채팅방에 들어온 경우 구매자가 메세지 sender, 판매자가 메세지 receiver가 된다.
        else {
            Chat chatRoom = findChatRoom.get();

            if (findMember.equals(chatRoom.getBuyer())) {
                senderId = chatRoom.getBuyer().getId();
                senderName = chatRoom.getBuyer().getNickname();
                receiverId = chatRoom.getSeller().getId();
                receiverName = chatRoom.getSeller().getNickname();
            } else {
                senderId = chatRoom.getSeller().getId();
                senderName = chatRoom.getSeller().getNickname();
                receiverId = chatRoom.getBuyer().getId();
                receiverName = chatRoom.getBuyer().getNickname();
            }
        }

        ChatResponse chatInfo = ChatResponse.builder()
                .senderId(senderId)
                .senderName(senderName)
                .boardId(findBoard.getId())
                .boardTitle(findBoard.getTitle())
                .boardImage(findBoard.getImage())
                .boardPrice(findBoard.getPrice())
                .receiverId(receiverId)
                .receiverName(receiverName)
                .roomNum(roomNum)
                .build();

        model.addAttribute("chatInfo", chatInfo);
        model.addAttribute("certifyYn", findMember.getCertifyYn());

        return "chat/chat";
    }
}
