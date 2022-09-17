package js.daangnclone.service.comment;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.domain.comment.CommentRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.comment.dto.CommentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Comment writeComment(CommentForm commentForm, Board board, Member member) {
        Comment comment = new Comment();
        comment.setContent(commentForm.getContent());
        comment.setBoard(board);
        comment.setMember(member);

        return commentRepository.save(comment);
    }
}
