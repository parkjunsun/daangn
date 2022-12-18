package js.daangnclone.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //400 BAD_REQUEST : 잘못된 요청
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "E1O1", "접근 토큰이 유효하지 않습니다."),
    MISMATCH_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "E1O2","접근 토큰의 유저 정보가 일치하지 않습니다."),
    MISMATCH_AUTH_CODE(HttpStatus.BAD_REQUEST, "E1O3","인증코드가 일치하지 않습니다."),
    MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST, "E1O4","비밀번호가 일치하지 않습니다."),
    MISMATCH_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "E1O5","이전 비밀번호가 잘못 입력되었습니다. 다시 입력해주세요."),
    MISMATCH_CONFIRM_PASSWORD(HttpStatus.BAD_REQUEST, "E1O6","두 비밀번호가 일치하는지 확인하세요."),


    //401 UNAUTHORIZED : 인증되지 않은 사용자
    NOT_CERTIFIED_EMAIL(HttpStatus.UNAUTHORIZED, "E201","이메일 인증이 되지 않았습니다. 이메일 활성화 후 다시 로그인해주세요."),
    NOT_CERTIFIED_LOCATION(HttpStatus.UNAUTHORIZED, "E202","동네 인증이 되지 않았습니다.<br>이 기능을 사용하려면 동네인증이 필요해요."),

    //404 NOT_FOUND : Resource 를 찾을 수 없음
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E3O1","해당 유저 정보를 찾을 수 없습니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "E302", "해당 게시글 정보를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "E303", "해당 댓글 정보를 찾을 수 없습니다."),
    ATTENTION_NOT_FOUND(HttpStatus.NOT_FOUND, "E304", "해당 관심 정보를 찾을 수 업습니다."),
    LIKES_NOT_FOUND(HttpStatus.NOT_FOUND, "E305", "해당 좋아요 정보를 찾을 수 없습니다."),
    ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "E306", "해당 알람 정보를 찾을 수 없습니다."),
    CHAT_NOT_FOUND(HttpStatus.NOT_FOUND, "E307", "해당 채팅 정보를 찾을 수 없습니다."),
    CHAT_NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "E308", "해당 채팅알람 정보를 찾을 수 없습니다"),
    KEYWORD_NOT_FOUND(HttpStatus.NOT_FOUND, "E309", "해당 키워드 정보를 찾을 수 없습니다."),
    KEYWORD_ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "E310", "해당 키워드 알림 정보를 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "E311", "해당 리뷰 정보를 찾을 수 없습니다."),

    //409 CONFLICT : Resource 의 현재 상태와 충돌, 보통 중복된 데이터 존재
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "E4O1","중복된 EMAIL 이 존재합니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "E4O2","이미 존재하는 USERNAME 입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "E403", "이미 존재하는 NICKNAME 입니다."),
    OVERSIZE_KEYWORD(HttpStatus.CONFLICT, "E404", "키워드 등록 제한수를 초과했습니다."),
    HAS_NOT_NEXT_PAGE(HttpStatus.CONFLICT, "E405", "다음 페이지가 없습니다"),
    DUPLICATE_REVIEW(HttpStatus.CONFLICT, "E406", "이미 존재하는 REVIEW 입니다."),
    NOT_VERIFIABLE_REVIEW(HttpStatus.CONFLICT, "E407", "확인 불가능한 REVIEW 입니다.");


    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String detail;
}
