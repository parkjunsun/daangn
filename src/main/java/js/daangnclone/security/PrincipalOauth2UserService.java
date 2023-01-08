package js.daangnclone.security;

import js.daangnclone.exception.CustomException;
import js.daangnclone.exception.ErrorCode;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRepository;
import js.daangnclone.domain.member.MemberRole;
import js.daangnclone.security.provider.GoogleUserInfo;
import js.daangnclone.security.provider.KakaoUserInfo;
import js.daangnclone.security.provider.NaverUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("oAuth2User = {}", oAuth2User);

        Oauth2UserInfo oauth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oauth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oauth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oauth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        String providerId = oauth2UserInfo.getProviderId();
        String provider = oauth2UserInfo.getProvider();

        String username = provider + "-" + providerId;
        String password = passwordEncoder.encode(UUID.randomUUID().toString());
        String nickname = oauth2UserInfo.getName();
        String email = oauth2UserInfo.getEmail();

        Optional<Member> findOauthMember = memberRepository.findByUsername(username);
        if (findOauthMember.isPresent()) {
            Member member = findOauthMember.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            return new PrincipalUserDetails(member, oAuth2User.getAttributes());
        } else {
            Member m = Member.builder()
                    .provider(provider)
                    .username(username)
                    .password(password)
                    .nickname(nickname)
                    .email(email)
                    .memberRole(MemberRole.ROLE_USER)
                    .certifyYn("N")
                    .build();
            Member member = memberRepository.save(m);
            return new PrincipalUserDetails(member, oAuth2User.getAttributes());
        }
    }
}
