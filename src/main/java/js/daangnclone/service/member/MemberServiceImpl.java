package js.daangnclone.service.member;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static js.daangnclone.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    @Transactional
    @Override
    public void updateMemberCertifyYn(Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        findMember.setCertifyYn("Y");
    }

    @Override
    public void validateCertifyLocation(Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        String certifyYn = findMember.getCertifyYn();

        if (certifyYn.equals("N")) {
            throw new CustomException(NOT_CERTIFIED_LOCATION);
        }
    }
}
