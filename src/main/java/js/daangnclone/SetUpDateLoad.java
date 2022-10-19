package js.daangnclone;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.Exception.ErrorCode;
import js.daangnclone.cmn.Area;
import js.daangnclone.cmn.Category;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.board.BoardRepository;
import js.daangnclone.domain.board.BoardStatus;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRepository;
import js.daangnclone.domain.member.MemberRole;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static js.daangnclone.Exception.ErrorCode.USER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class SetUpDateLoad implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        createMemberIfNotFound();
        createBoard();
        alreadySetup = true;
    }

    private void createMemberIfNotFound() {
        if (memberRepository.findByUsername("kakao-1796091073").isEmpty()) {
            Member member = Member.builder()
                    .username("kakao-1796091073")
                    .nickname("messi1")
                    .email("junsun7190@daum.net")
                    .password(passwordEncoder.encode("1234"))
                    .provider("kakao")
                    .area(Area.ANYANG_DONGAN)
                    .memberRole(MemberRole.ROLE_USER)
                    .certifyYn("Y")
                    .build();
            memberRepository.save(member);
        }

        if (memberRepository.findByUsername("naver-_IgsVw9FnC90aXXL9993KHqSgY73U2HTHX86XHMWzfU").isEmpty()) {
            Member member = Member.builder()
                    .username("naver-_IgsVw9FnC90aXXL9993KHqSgY73U2HTHX86XHMWzfU")
                    .nickname("park95")
                    .email("qkrwnstns52@naver.com")
                    .password(passwordEncoder.encode("1234"))
                    .provider("naver")
                    .area(Area.ANYANG_DONGAN)
                    .memberRole(MemberRole.ROLE_USER)
                    .certifyYn("Y")
                    .build();
            memberRepository.save(member);
        }

        if (memberRepository.findByUsername("google-107231215495405360973").isEmpty()) {
            Member member = Member.builder()
                    .username("google-107231215495405360973")
                    .nickname("호날두")
                    .email("qkrwnstns52@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .provider("google")
                    .area(Area.ANYANG_DONGAN)
                    .memberRole(MemberRole.ROLE_USER)
                    .certifyYn("Y")
                    .build();
            memberRepository.save(member);
        }
    }

    private void createBoard() {

        Member member = memberRepository.findByUsername("kakao-1796091073").orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Board board = Board.builder()
                .title("보테가 팝니다~")
                .category(Category.of(106001L))
                .content("저렴하게 팝니다.\n직거래 선호합니다\n연락주세요\n".replace("\r\n", "<br>"))
                .detail("보테가 베네타")
                .price(750000)
                .boardStatus(BoardStatus.SALE_ON)
                .image("https://contents.lotteon.com/itemimage/_v182940/LE/12/11/46/39/30/_1/26/48/67/05/9/LE1211463930_1264867059_1.jpg/dims/resizef/554X554")
                .member(member)
                .build();

        boardRepository.save(board);
    }


}
