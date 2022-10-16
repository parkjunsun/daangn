package js.daangnclone.service.chat;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.domain.attention.event.AttentionCreatedEvent;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.chat.ChatRepository;
import js.daangnclone.domain.chat.event.ChatCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static js.daangnclone.Exception.ErrorCode.CHAT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;
    private final ApplicationEventPublisher eventPublisher;  //이벤트를 발생시키기 위한 bean 주입 *EventPublisher를 사용함으로써 결합도가 낮아진다

    @Override
    @Transactional
    public Chat createChatRoom(Chat chat) {
        eventPublisher.publishEvent(new ChatCreatedEvent(chat));
        return chatRepository.save(chat);
    }

    @Override
    public Optional<Chat> findChatRoom(String roomNum) {
        return chatRepository.findByRoomNum(roomNum);
    }


    @Override
    public long getCountChatRoom(Board board) {
        return chatRepository.countByBoard(board);
    }
}
