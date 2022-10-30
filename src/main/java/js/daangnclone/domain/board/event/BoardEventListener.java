package js.daangnclone.domain.board.event;

import js.daangnclone.domain.activity.Activity;
import js.daangnclone.domain.activity.ActivityType;
import js.daangnclone.domain.alarm.keywordAlarm.KeywordAlarm;
import js.daangnclone.domain.board.Board;
import js.daangnclone.service.activity.ActivityService;
import js.daangnclone.service.alarm.keywordAlarm.KeywordAlarmService;
import js.daangnclone.service.keyword.KeywordService;
import js.daangnclone.web.keyword.dto.KeywordEventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Async
@Component
@Slf4j
@RequiredArgsConstructor
public class BoardEventListener {

    private final ActivityService activityService;
    private final KeywordService keywordService;
    private final KeywordAlarmService keywordAlarmService;

    @EventListener //이벤트리스너 명시
    public void handleBoardCreatedEvent(BoardCreatedEvent event) {
        Board board = event.getBoard();

        Activity newActivity = Activity.builder()
                .message("새로운 상품을 등록하였습니다.")
                .content(board.getTitle())
                .link("/board/" + board.getId())
                .activityType(ActivityType.BOARD_CREATE)
                .sender(board.getMember())
                .receiver(board.getMember())
                .createdAt(LocalDateTime.now())
                .build();

        activityService.load(newActivity);

        List<KeywordEventResponse> keywordList = keywordService.inquireAllKeywordList();
        for (KeywordEventResponse keywordEvent : keywordList) {
            if (board.getTitle().contains(keywordEvent.getWord())) {
                KeywordAlarm keywordAlarm = KeywordAlarm.builder()
                        .board(board)
                        .receiver(keywordEvent.getMember())
                        .checkedYn("N")
                        .clickYn("N")
                        .createdAt(LocalDateTime.now())
                        .build();

                keywordAlarmService.load(keywordAlarm);
            }
        }
    }
}
