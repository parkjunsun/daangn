package js.daangnclone.service.chat;

import js.daangnclone.domain.chat.Chat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatService {

    Flux<Chat> findChatRoom(String roomNum);
    Mono<Chat> createChatRoom(Chat chat);

}
