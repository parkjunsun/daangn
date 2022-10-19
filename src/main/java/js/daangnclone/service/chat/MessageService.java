package js.daangnclone.service.chat;

import js.daangnclone.domain.chat.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {

    Flux<Message> findChatRoom(String roomNum);
    Mono<Message> createMessage(Message message);

}
