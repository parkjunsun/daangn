package js.daangnclone.service.mail;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.cmn.AESCryptoUtil;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static js.daangnclone.Exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    @Value("${spring.mail.username}")
    private String FROM_EMAIL;

    public void sendMail(String TO_EMAIL) throws Exception {

        Member findMember = memberRepository.findByEmail(TO_EMAIL).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        String username = findMember.getUsername();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(TO_EMAIL);
        message.setFrom(FROM_EMAIL);
        message.setSubject("Daangn Market 아이디 / 패스워드 찾기");
        message.setText("안녕하세요.\n\n +" +
                        "회원님의 아이디는 [" + username + "] 입니다.\n\n" +
                        "혹시 비밀번호를 모르시겠으면, 아래 링크를 이용해서 초기화 해주세요.\n" +
                        "http://localhost:8080/password/reset/" + AESCryptoUtil.encrypt(username));
        javaMailSender.send(message);

    }
}
