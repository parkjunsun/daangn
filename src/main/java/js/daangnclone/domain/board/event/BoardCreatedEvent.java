package js.daangnclone.domain.board.event;

import js.daangnclone.domain.board.Board;
import lombok.Getter;

/**
 * 관심 생성시 발생시킬 이벤트
 */

@Getter
public class BoardCreatedEvent {

    private final Board board;

    public BoardCreatedEvent(Board board) {
        this.board = board;
    }
}
