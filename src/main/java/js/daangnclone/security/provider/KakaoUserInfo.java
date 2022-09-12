package js.daangnclone.security.provider;

import js.daangnclone.security.Oauth2UserInfo;

import java.util.Map;

public class KakaoUserInfo implements Oauth2UserInfo {

    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        Map properties = (Map) attributes.get("kakao_account");
        return properties.get("email").toString();
    }

    @Override
    public String getName() {
        Map properties = (Map) attributes.get("properties");
        return properties.get("nickname").toString();
    }
}
