package js.daangnclone.service.comment;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.comment.dto.CommentForm;
import js.daangnclone.web.comment.dto.CommentResponse;

import java.util.List;

public interface CommentService {

    Comment writeComment(CommentForm commentForm, Board board, Member member);
    List<CommentResponse> inquireCommentList(Long boardId, Long memberId);

}
