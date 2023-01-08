package js.daangnclone.service.chatNotification;

import js.daangnclone.domain.chatNotification.ChatNotification;
import js.daangnclone.domain.chatNotification.ChatNotificationRepository;
import js.daangnclone.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatNotificationServiceImpl implements ChatNotificationService{

    private final ChatNotificationRepository chatNotificationRepository;

    @Override
    @Transactional
    public void load(ChatNotification chatNotification) {
        chatNotificationRepository.save(chatNotification);
    }


    @Override
    public List<ChatNotification> findChatNotificationList(String roomNum, Member receiver) {
        return chatNotificationRepository.findByRoomNumAndReceiver(roomNum, receiver);
    }

    @Override
    @Transactional
    public void markAsRead(List<ChatNotification> chatNotificationList) {
        chatNotificationList.forEach(ChatNotification::read);
    }

    @Override
    @Transactional
    public void markAsClick(ChatNotification chatNotification) {
        chatNotification.click();
    }

}
