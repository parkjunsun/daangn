package js.daangnclone.service.chat;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.domain.attention.event.AttentionCreatedEvent;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.chat.ChatRepository;
import js.daangnclone.domain.chat.event.ChatCreatedEvent;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.chat.dto.ChatResponse;
import js.daangnclone.web.chatNotification.dto.ChatListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<ChatListResponse> findAllChatRoom(Member member) {
        List<Chat> chatList = chatRepository.findBySellerOrBuyer(member);
        List<ChatListResponse> newChatList = new ArrayList<>();

        for (Chat chat : chatList) {
            if (chat.getSeller().equals(member)) {
                newChatList.add(
                        ChatListResponse.builder()
                        .link("/board/" + chat.getBoard().getId() + "/chat?roomNum=" + chat.getRoomNum())
                        .opponentName(chat.getBuyer().getNickname())
                        .opponentAddress(chat.getBuyer().getArea().getAreaName())
                        .boardImage(chat.getBoard().getImage()).build());
            } else {
                newChatList.add(
                        ChatListResponse.builder()
                                .link("/board/" + chat.getBoard().getId() + "/chat?roomNum=" + chat.getRoomNum())
                                .opponentName(chat.getSeller().getNickname())
                                .opponentAddress(chat.getSeller().getArea().getAreaName())
                                .boardImage(chat.getBoard().getImage()).build());
            }
        }

        return newChatList;
    }
}
