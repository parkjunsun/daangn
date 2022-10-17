package js.daangnclone.service.chat;

import js.daangnclone.domain.chat.Message;
import js.daangnclone.domain.chat.MessageRepository;
import js.daangnclone.domain.chat.event.ChatCreatedEvent;
import js.daangnclone.domain.chat.event.MessageCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ApplicationEventPublisher eventPublisher;  //이벤트를 발생시키기 위한 bean 주입 *EventPublisher를 사용함으로써 결합도가 낮아진다

    @Override
    public Flux<Message> findChatRoom(String roomNum) {
        return messageRepository.mFindByRoomNum(roomNum);
    }

    @Override
    @Transactional
    public Mono<Message> createChatRoom(Message message) {
        eventPublisher.publishEvent(new MessageCreatedEvent(message));
        return messageRepository.save(message);
    }
}
