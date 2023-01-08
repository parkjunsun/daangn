package js.daangnclone.service.member;

import js.daangnclone.exception.CustomException;
import js.daangnclone.cmn.area.Area;
import js.daangnclone.domain.board.BoardRepository;
import js.daangnclone.domain.board.BoardStatus;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRepository;
import js.daangnclone.domain.member.MemberRole;
import js.daangnclone.web.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static js.daangnclone.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Member save(MemberForm memberForm) {
        Member member = Member.builder()
                .provider("daanngn")
                .username(memberForm.getUsername())
                .nickname(memberForm.getNickname())
                .password(passwordEncoder.encode(memberForm.getPassword()))
                .email(memberForm.getEmail())
//                .area(areaRepository.findById(memberForm.getCity()).get())
                .area(Area.of(memberForm.getCity()))
                .memberRole(MemberRole.ROLE_USER)
                .certifyYn("N")
                .build();
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

    @Override
    public String validateDuplicateUsername(String username) {
        Optional<Member> findMember = memberRepository.findByUsername(username);

        if (findMember.isPresent()) {
            return "N";
        } else {
            return "Y";
        }
    }

    @Override
    public String validateDuplicateNickname(String nickname) {
        Optional<Member> findMember = memberRepository.findByNickname(nickname);

        if (findMember.isPresent()) {
            return "N";
        } else {
            return "Y";
        }
    }

    @Override
    @Transactional
    public void addDetails(Long id, MemberDetailsForm detailsForm) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        findMember.setNickname(detailsForm.getNickname());
//        findMember.setArea(areaRepository.findById(detailsForm.getCity()).get());
        findMember.setArea(Area.of(detailsForm.getCity()));
    }

    @Override
    @Transactional
    public void updateMemberProfile(Long id, ProfileForm profileForm) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        findMember.setNickname(profileForm.getNickname());
        findMember.setEmail(profileForm.getEmail());
        findMember.setPassword(passwordEncoder.encode(profileForm.getPassword()));
    }

    @Override
    @Transactional
    public void updateMemberAddress(Long id, AddressForm addressForm) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        findMember.setCertifyYn("N");
        findMember.setArea(Area.of(addressForm.getCity()));
    }

    @Override
    public ProfileResponse inquireProfile(Long id) {

        Member findMember = memberRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        long numberOfSales = boardRepository.countByMemberAndBoardStatus(findMember, BoardStatus.SALE_COMP);
        long amountOfSales = boardRepository.sumByBoardStatusGroupByMember(findMember, BoardStatus.SALE_COMP);
        long numberOfPurchases = boardRepository.countByPurchaserAndBoardStatus(findMember, BoardStatus.SALE_COMP);
        long amountOfPurchases = boardRepository.sumByBoardStatusGroupByPurchaser(findMember, BoardStatus.SALE_COMP);

        return ProfileResponse.builder()
                .reviewScore(findMember.getReviewScore())
                .numberOfSales(numberOfSales)
                .amountOfSales(amountOfSales)
                .numberOfPurchases(numberOfPurchases)
                .amountOfPurchases(amountOfPurchases)
                .build();
    }

    @Override
    @Transactional
    public void updateMemberPassword(String username, String password) {
        Member findMember = memberRepository.findByUsername(username).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        findMember.setPassword(passwordEncoder.encode(password));
    }
}
