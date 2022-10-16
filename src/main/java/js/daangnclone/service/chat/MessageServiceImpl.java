package js.daangnclone.service.chat;

import js.daangnclone.domain.chat.Message;
import js.daangnclone.domain.chat.MessageRepository;
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
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Flux<Message> findChatRoom(String roomNum) {
        return messageRepository.mFindByRoomNum(roomNum);
    }

    @Override
    @Transactional
    public Mono<Message> createChatRoom(Message chat) {
        return messageRepository.save(chat);
    }
}
