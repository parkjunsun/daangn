package js.daangnclone.config;

import js.daangnclone.cmn.CurrentMemberArgumentResolver;
import js.daangnclone.domain.alarm.activityAlarm.interceptor.ActivityAlarmInterceptor;
import js.daangnclone.domain.alarm.keywordAlarm.interceptor.KeywordAlarmInterceptor;
import js.daangnclone.domain.chatNotification.interceptor.ChatNotificationInterceptor;
import js.daangnclone.handler.GlobalHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final GlobalHandlerInterceptor globalHandlerInterceptor;
    private final ActivityAlarmInterceptor activityAlarmInterceptor;
    private final ChatNotificationInterceptor chatNotificationInterceptor;
    private final KeywordAlarmInterceptor keywordAlarmInterceptor;
    private final CurrentMemberArgumentResolver currentMemberArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> staticResourcePath = Stream.of(StaticResourceLocation.values())
                .flatMap(StaticResourceLocation::getPatterns)
                .collect(Collectors.toList());
        staticResourcePath.add("/favicon.ico");
        staticResourcePath.add("/resources/**");
        staticResourcePath.add("/error");

        registry.addInterceptor(globalHandlerInterceptor).excludePathPatterns(staticResourcePath);
        registry.addInterceptor(activityAlarmInterceptor).excludePathPatterns(staticResourcePath);
        registry.addInterceptor(chatNotificationInterceptor).excludePathPatterns(staticResourcePath);
        registry.addInterceptor(keywordAlarmInterceptor).excludePathPatterns(staticResourcePath);

    }

    /**
     * 커스텀 ArgumentResolver 등록
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentMemberArgumentResolver);
    }
}
