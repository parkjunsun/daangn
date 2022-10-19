package js.daangnclone.service.chat;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.chat.dto.ChatResponse;
import js.daangnclone.web.chatNotification.dto.ChatListResponse;

import java.util.List;
import java.util.Optional;

public interface ChatService {

    Chat createChatRoom(Chat chat);
    Optional<Chat> findChatRoom(String roomNum);
    long getCountChatRoom(Board board);
    List<ChatListResponse> findAllChatRoom(Member member);
    void updateLastComment(Chat chat, String comment);
}
