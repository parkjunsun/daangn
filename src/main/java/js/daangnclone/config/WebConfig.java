package js.daangnclone.config;

import js.daangnclone.domain.alarm.interceptor.AlarmInterceptor;
import js.daangnclone.domain.chatNotification.interceptor.ChatNotificationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AlarmInterceptor alarmInterceptor;
    private final ChatNotificationInterceptor chatNotificationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> staticResourcePath = Stream.of(StaticResourceLocation.values())
                .flatMap(StaticResourceLocation::getPatterns)
                .collect(Collectors.toList());
        staticResourcePath.add("/favicon.ico");
        staticResourcePath.add("/resources/**");
        staticResourcePath.add("/error");
        registry.addInterceptor(alarmInterceptor)
                .excludePathPatterns(staticResourcePath);
        registry.addInterceptor(chatNotificationInterceptor)
                .excludePathPatterns(staticResourcePath);
    }
}
