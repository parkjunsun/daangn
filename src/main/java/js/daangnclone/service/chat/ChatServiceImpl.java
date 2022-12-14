package js.daangnclone.service.chat;

import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.chat.ChatRepository;
import js.daangnclone.domain.chat.event.ChatCreatedEvent;
import js.daangnclone.domain.chatNotification.ChatNotification;
import js.daangnclone.domain.chatNotification.ChatNotificationRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.chatNotification.dto.ChatListResponse;
import js.daangnclone.web.sale.dto.PurchaserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;
    private final ChatNotificationRepository chatNotificationRepository;
    private final ApplicationEventPublisher eventPublisher;

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

                List<ChatNotification> chatNotificationList = chatNotificationRepository.findByRoomNumAndReceiver(chat.getRoomNum(), chat.getSeller());
                String checkedYn = "Y";
                for (ChatNotification chatNotification : chatNotificationList) {
                    if (chatNotification.getCheckedYn().equals("N")) {
                        checkedYn = "N";
                        break;
                    }
                }

                newChatList.add(
                        ChatListResponse.builder()
                                .link("/board/" + chat.getBoard().getId() + "/chat?roomNum=" + chat.getRoomNum())
                                .opponentName(chat.getBuyer().getNickname())
                                .opponentAddress(chat.getBuyer().getArea().getAreaName())
                                .boardTitle(chat.getBoard().getTitle())
                                .boardImage(chat.getBoard().getImage())
                                .boardStatus(chat.getBoard().getBoardStatus())
                                .lastComment(chat.getLastComment())
                                .checkedYn(checkedYn)
                                .diffCreatedAt(DateUtil.diffDate(chat.getCreatedAt())).build());
            } else {

                List<ChatNotification> chatNotificationList = chatNotificationRepository.findByRoomNumAndReceiver(chat.getRoomNum(), chat.getBuyer());
                String checkedYn = "Y";
                for (ChatNotification chatNotification : chatNotificationList) {
                    if (chatNotification.getCheckedYn().equals("N")) {
                        checkedYn = "N";
                        break;
                    }
                }

                newChatList.add(
                        ChatListResponse.builder()
                                .link("/board/" + chat.getBoard().getId() + "/chat?roomNum=" + chat.getRoomNum())
                                .opponentName(chat.getSeller().getNickname())
                                .opponentAddress(chat.getSeller().getArea().getAreaName())
                                .boardTitle(chat.getBoard().getTitle())
                                .boardImage(chat.getBoard().getImage())
                                .boardStatus(chat.getBoard().getBoardStatus())
                                .lastComment(chat.getLastComment())
                                .checkedYn(checkedYn)
                                .diffCreatedAt(DateUtil.diffDate(chat.getCreatedAt())).build());
            }
        }

        return newChatList;
    }

    @Override
    public List<ChatListResponse> findChatRoomInBoard(Member member, Board board) {
        List<Chat> chatList = chatRepository.findBySellerAndBoard(member, board);
        List<ChatListResponse> newChatList = new ArrayList<>();

        for(Chat chat : chatList) {
            List<ChatNotification> chatNotificationList = chatNotificationRepository.findByRoomNumAndReceiver(chat.getRoomNum(), chat.getSeller());
            String checkedYn = "Y";
            for (ChatNotification chatNotification : chatNotificationList) {
                if (chatNotification.getCheckedYn().equals("N")) {
                    checkedYn = "N";
                    break;
                }
            }

            newChatList.add(
                    ChatListResponse.builder()
                            .link("/board/" + chat.getBoard().getId() + "/chat?roomNum=" + chat.getRoomNum())
                            .opponentName(chat.getBuyer().getNickname())
                            .opponentAddress(chat.getBuyer().getArea().getAreaName())
                            .boardTitle(chat.getBoard().getTitle())
                            .boardImage(chat.getBoard().getImage())
                            .boardStatus(chat.getBoard().getBoardStatus())
                            .lastComment(chat.getLastComment())
                            .checkedYn(checkedYn)
                            .diffCreatedAt(DateUtil.diffDate(chat.getCreatedAt()))
                            .build());
        }

        return newChatList;

    }

    @Override
    @Transactional
    public void updateLastComment(Chat chat, String comment) {
        chat.setLastComment(comment);
        chat.setLastCommentUpdatedAt(LocalDateTime.now());
    }

    @Override
    public List<PurchaserResponse> findChatList(Board board) {
        List<Chat> chatList = chatRepository.findByBoard(board);

        return chatList.stream()
                .map(chat -> PurchaserResponse.builder()
                        .sender(chat.getBuyer())
                        .board(chat.getBoard())
                        .roomNum(chat.getRoomNum())
                        .diffLastCommentUpdatedAt(DateUtil.diffDate(chat.getLastCommentUpdatedAt()))
                        .build())
                .collect(Collectors.toList());
    }
}
