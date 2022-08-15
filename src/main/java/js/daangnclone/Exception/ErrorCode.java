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

    //404 NOT_FOUND : Resource 를 찾을 수 없음
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E3O1","해당 유저 정보를 찾을 수 없습니다."),

    //409 CONFLICT : Resource 의 현재 상태와 충돌, 보통 중복된 데이터 존재
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "E4O1","중복된 EMAIL 이 존재합니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "E4O2","이미 존재하는 USERNAME 입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "E403", "이미 존재하는 NICKNAME 입니다.")


    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String detail;
}
