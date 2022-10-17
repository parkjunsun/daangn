package js.daangnclone.service.chat;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.chat.Chat;

import java.util.Optional;

public interface ChatService {

    Chat createChatRoom(Chat chat);
    Optional<Chat> findChatRoom(String roomNum);
    long getCountChatRoom(Board board);
}