package js.daangnclone.handler;

import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRepository;
import js.daangnclone.security.PrincipalUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GlobalHandlerInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (modelAndView != null && !isRedirectView(modelAndView) && authentication != null && isTypeOfMember(authentication)) {
            Member findMember = ((PrincipalUserDetails) authentication.getPrincipal()).getMember();
            long areaMemberCnt = memberRepository.countByArea(findMember.getArea());

            modelAndView.addObject("areaName", findMember.getArea().getAreaName());
            modelAndView.addObject("areaMemberCnt", areaMemberCnt);
            modelAndView.addObject("nickname", findMember.getNickname());
            modelAndView.addObject("certifyYn", findMember.getCertifyYn());
        }

    }

    private boolean isRedirectView(ModelAndView modelAndView) {
        Optional<ModelAndView> optionalModelAndView = Optional.ofNullable(modelAndView);
        return startsWithRedirect(optionalModelAndView) || isTypeOfRedirectView(optionalModelAndView);
    }


    private boolean startsWithRedirect(Optional<ModelAndView> optionalModelAndView) {
        return optionalModelAndView.map(ModelAndView::getViewName)
                .map(viewName -> viewName.startsWith("redirect:"))
                .orElse(false);
    }

    private boolean isTypeOfRedirectView(Optional<ModelAndView> optionalModelAndView) {
        return optionalModelAndView.map(ModelAndView::getView)
                .map(v -> v instanceof RedirectView)
                .orElse(false);
    }

    private boolean isTypeOfMember(Authentication authentication) {
        return authentication.getPrincipal() instanceof PrincipalUserDetails;
    }
}
