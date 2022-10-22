package js.daangnclone.domain.board;

import js.daangnclone.cmn.Area;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardStatus {

    SALE_ON("01", "판매중"),
    SALE_COMP("02", "판매완료"),
    SALE_RSRVT("03", "예약완료");

    private final String key;
    private final String value;


    public static BoardStatus of(String boardStatusCd) {
        if (boardStatusCd == null) {
            throw new IllegalArgumentException();
        }

        for (BoardStatus boardStatus : BoardStatus.values()) {
            if (boardStatus.key.equals(boardStatusCd)) {
                return boardStatus;
            }
        }

        throw new IllegalArgumentException("일치하는 상태코드가 없습니다.");
    }

}
