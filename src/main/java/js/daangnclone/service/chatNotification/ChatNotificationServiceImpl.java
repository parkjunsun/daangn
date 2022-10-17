package js.daangnclone.service.chatNotification;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.Exception.ErrorCode;
import js.daangnclone.domain.chatNotification.ChatNotification;
import js.daangnclone.domain.chatNotification.ChatNotificationRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.chatNotification.dto.ChatNotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static js.daangnclone.Exception.ErrorCode.CHAT_NOTIFICATION_NOT_FOUND;

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
    public ChatNotification findChatNotification(Long chatNotificationId) {
        return chatNotificationRepository.findById(chatNotificationId).orElseThrow(() -> new CustomException(CHAT_NOTIFICATION_NOT_FOUND));
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

    @Override
    public List<ChatNotificationResponse> inquireChatNotificationList(Member receiver, String checkedYn) {
        return null;
    }
}