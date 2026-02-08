package js.daangnclone.cmn;

import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * {@code @CurrentMember} 어노테이션이 붙은 파라미터에 현재 로그인한 Member를 자동 주입
 */
@Component
@RequiredArgsConstructor
public class CurrentMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    /**
     * 이 Resolver가 해당 파라미터를 처리할 수 있는지 판단
     *
     * @return true면 resolveArgument() 메서드 호출
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 1. @CurrentMember 어노테이션이 있는지 확인
        boolean hasAnnotation = parameter.hasParameterAnnotation(CurrentMember.class);

        // 2. 파라미터 타입이 Member인지 확인
        boolean isMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasAnnotation && isMemberType;
    }

    /**
     * 실제로 파라미터에 주입할 객체를 생성/반환
     */
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        // SecurityContext에서 현재 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증되지 않았거나 PrincipalUserDetails가 아닌 경우 null 반환
        if (authentication == null ||
            !(authentication.getPrincipal() instanceof PrincipalUserDetails)) {
            return null;
        }

        // PrincipalUserDetails에서 Member ID 추출
        PrincipalUserDetails userDetails = (PrincipalUserDetails) authentication.getPrincipal();
        Long memberId = userDetails.getMember().getId();

        // DB에서 Member 조회하여 반환
        return memberService.findMember(memberId);
    }
}

