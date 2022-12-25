package js.daangnclone.service.member;

import js.daangnclone.domain.member.Member;
import js.daangnclone.web.member.dto.*;

import java.util.List;

public interface MemberService {

    Member save(MemberForm memberForm);
    Member findMember(Long id);
    void updateMemberCertifyYn(Long id);
    void validateCertifyLocation(Long id);
    String validateDuplicateUsername(String username);
    String validateDuplicateNickname(String nickname);
    void addDetails(Long id, MemberDetailsForm addressForm);
    void updateMemberProfile(Long id, ProfileForm profileForm);
    void updateMemberAddress(Long id, AddressForm addressForm);
    ProfileResponse inquireProfile(Long id);
    void updateMemberPassword(String username, String password);
}
