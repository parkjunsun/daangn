package js.daangnclone.service.member;

import js.daangnclone.domain.member.Member;

public interface MemberService {

    Member save(Member member);
    Member findMember(Long id);
    void updateMemberCertifyYn(Long id);
    void validateCertifyLocation(Long id);
}
