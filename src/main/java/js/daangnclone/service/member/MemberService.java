package js.daangnclone.service.member;

import js.daangnclone.domain.member.Member;
import js.daangnclone.web.member.dto.MemberForm;

public interface MemberService {

    Member save(MemberForm memberForm);
    Member findMember(Long id);
    void updateMemberCertifyYn(Long id);
    void validateCertifyLocation(Long id);
    String validateDuplicateUsername(String username);
    String validateDuplicateNickname(String nickname);
}
