package js.daangnclone.service.chat;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.chatNotification.dto.ChatListResponse;
import js.daangnclone.web.sale.dto.PurchaserResponse;

import java.util.List;
import java.util.Optional;

public interface ChatService {

    Chat createChatRoom(Chat chat);
    Optional<Chat> findChatRoom(String roomNum);
    long getCountChatRoom(Board board);
    List<ChatListResponse> findAllChatRoom(Member member);
    List<ChatListResponse> findChatRoomInBoard(Member member, Board board);
    void updateLastComment(Chat chat, String comment);
    List<PurchaserResponse> findChatList(Board board);
}
