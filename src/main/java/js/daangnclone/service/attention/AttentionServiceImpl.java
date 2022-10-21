package js.daangnclone.service.attention;

import js.daangnclone.domain.attention.Attention;
import js.daangnclone.domain.attention.event.AttentionCreatedEvent;
import js.daangnclone.domain.attention.AttentionRepository;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttentionServiceImpl implements AttentionService{

    private final AttentionRepository attentionRepository;
    private final ApplicationEventPublisher eventPublisher;  //이벤트를 발생시키기 위한 bean 주입 *EventPublisher를 사용함으로써 결합도가 낮아진다

    @Override
    @Transactional
    public void processAttention(Member member, Board board) {

        Optional<Attention> findAttention = attentionRepository.findByMemberAndBoard(member, board);

        if (findAttention.isPresent()) {
            deleteAttention(member, board);
        } else {
            registerAttention(member, board);
        }
    }

    private void registerAttention(Member member, Board board) {
        Attention attention = new Attention();
        attention.setMember(member);
        attention.setBoard(board);

        //로그인 사용자와 게시글 사용자가 다를 때만 관심 알림 이벤트를 생성한다.
        if (!member.equals(board.getMember())) {
            eventPublisher.publishEvent(new AttentionCreatedEvent(attention));
        }
        //관심이 만들어지는 시점에 이벤트를 발생시킨다.
        //비동기처리(다른 스레드에서 처리)를 하지 않으면 여기서 RuntimeException이 발생했을 경우
        // @Transactional의 영향을 받게 되어 rollback이 발생하므로 주의해야한다.
        attentionRepository.save(attention);
    }

    private void deleteAttention(Member member, Board board) {
        attentionRepository.deleteByMemberAndBoard(member, board);
    }

    @Override
    public String getInpAttentionYn(Member member, Board board) {
        Optional<Attention> findAttention = attentionRepository.findByMemberAndBoard(member, board);
        String attentionInpYn = null;

        if (findAttention.isPresent()) {
            attentionInpYn = "Y";
        } else {
            attentionInpYn = "N";
        }

        return attentionInpYn;
    }

    @Override
    public long countAttentionInBoard(Board board) {
        return attentionRepository.countByBoard(board);
    }
}
