package js.daangnclone.cmn;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 현재 로그인한 Member 엔티티를 자동 주입하는 어노테이션
 *
 * 사용법:
 * public String method(@CurrentMember Member member) { ... }
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentMember {
}

