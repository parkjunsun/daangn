package js.daangnclone.service.comment;

import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.board.BoardRepository;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.domain.comment.CommentRepository;
import js.daangnclone.domain.like.Likes;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRepository;
import js.daangnclone.web.comment.dto.CommentForm;
import js.daangnclone.web.comment.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Comment writeComment(CommentForm commentForm, Board board, Member member) {
        Comment comment = new Comment();
        comment.setContent(commentForm.getContent());
        comment.setBoard(board);
        comment.setMember(member);

        return commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> inquireCommentList(Long boardId, Long memberId) {
        List<Comment> commentList = commentRepository.findByBoardId(boardId);
        Member findMember = memberRepository.findById(memberId).orElse(null);

        List<CommentResponse> commentResponseList = new ArrayList<>();

        for (Comment comment : commentList) {

            String likeInpYn = "N";

            List<Likes> likesList = comment.getLikeList();
            for (Likes likes : likesList) {
                if (Objects.equals(likes.getMember().getId(), memberId)) {
                    likeInpYn = "Y";
                }
            }

            CommentResponse commentResponse = CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .diffCreatedAt(DateUtil.diffDate(comment.getCreatedAt()))
                    .nickname(findMember.getNickname())
                    .city(findMember.getArea().getAreaName())
                    .provider(findMember.getProvider())
                    .likeInpYn(likeInpYn)
                    .build();

            commentResponseList.add(commentResponse);
        }

        return commentResponseList;
    }
}
