package js.daangnclone.service.chat;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static js.daangnclone.Exception.ErrorCode.CHAT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;

    @Override
    @Transactional
    public Chat createChatRoom(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public Optional<Chat> findChatRoom(String roomNum) {
        return chatRepository.findByRoomNum(roomNum);
    }
}
