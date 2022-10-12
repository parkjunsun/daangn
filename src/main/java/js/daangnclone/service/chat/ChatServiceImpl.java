package js.daangnclone.service.chat;

import js.daangnclone.domain.attention.event.AttentionCreatedEvent;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.chat.ChatRepository;
import js.daangnclone.domain.chat.event.ChatCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;
    private final ApplicationEventPublisher eventPublisher;  //이벤트를 발생시키기 위한 bean 주입 *EventPublisher를 사용함으로써 결합도가 낮아진다

    @Override
    public Flux<Chat> findChatRoom(String roomNum) {
        return chatRepository.mFindByRoomNum(roomNum);
    }

    @Override
    @Transactional
    public Mono<Chat> createChatRoom(Chat chat) {
        eventPublisher.publishEvent(new ChatCreatedEvent(chat));
        return chatRepository.save(chat);
    }
}
