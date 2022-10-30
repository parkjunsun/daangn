package js.daangnclone.domain.alarm.keywordAlarm.interceptor;

import js.daangnclone.domain.alarm.keywordAlarm.KeywordAlarmRepository;
import js.daangnclone.domain.member.Member;
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
public class KeywordAlarmInterceptor implements HandlerInterceptor {

    private final KeywordAlarmRepository keywordAlarmRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (modelAndView != null && !isRedirectView(modelAndView) && authentication != null && isTypeOfMember(authentication)) {
            Member receiver = ((PrincipalUserDetails) authentication.getPrincipal()).getMember();
            long count = keywordAlarmRepository.countByReceiverAndCheckedYn(receiver, "N");
            modelAndView.addObject("hasKeywordAlarm", count > 0);
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
