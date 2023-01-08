package js.daangnclone.security;

import js.daangnclone.exception.CustomException;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static js.daangnclone.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        if (member == null) {
            throw new UsernameNotFoundException("UsernameFoundException");
        }

        return new PrincipalUserDetails(member);
    }
}
