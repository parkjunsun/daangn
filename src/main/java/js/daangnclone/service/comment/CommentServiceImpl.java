package js.daangnclone.service.comment;

import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.domain.comment.CommentRepository;
import js.daangnclone.domain.comment.event.CommentCreatedEvent;
import js.daangnclone.domain.like.Likes;
import js.daangnclone.domain.like.LikesRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRepository;
import js.daangnclone.web.comment.dto.CommentForm;
import js.daangnclone.web.comment.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.events.CommentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Comment writeComment(CommentForm commentForm, Board board, Member member) {

        Comment comment = Comment.builder()
                .content(commentForm.getContent())
                .board(board)
                .member(member)
                .build();

        //로그인 사용자와 게시글 사용자가 다를 때만 댓글 알림 이벤트를 생성한다.
        if (!member.equals(board.getMember())) {
            eventPublisher.publishEvent(new CommentCreatedEvent(comment));
        }

        return commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> inquireCommentList(Long boardId, Long memberId) {
        List<Comment> commentList = commentRepository.findByBoardId(boardId);


        List<CommentResponse> commentResponseList = new ArrayList<>();

        for (Comment comment : commentList) {

            String likeInpYn = "N";

            List<Likes> likesList = comment.getLikeList();
            for (Likes likes : likesList) {
                if (Objects.equals(likes.getMember().getId(), memberId)) {
                    likeInpYn = "Y";
                    break;
                }
            }

            CommentResponse commentResponse = CommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .diffCreatedAt(DateUtil.diffDate(comment.getCreatedAt()))
                    .nickname(comment.getMember().getNickname())
                    .city(comment.getMember().getArea().getAreaName())
                    .provider(comment.getMember().getProvider())
                    .likeInpYn(likeInpYn)
                    .likeCnt(likesRepository.countByComment(comment))
                    .build();

            commentResponseList.add(commentResponse);
        }

        return commentResponseList;

//        return commentList.stream().map(comment -> convert(comment, findMember)).collect(Collectors.toList());
    }




//    CommentResponse convert(Comment comment, Member findMember) {
//
//        String likeInpYn = containsMember(comment.getLikeList(), findMember.getId()) ? "Y" : "N";
//
//        return CommentResponse.builder()
//                .id(comment.getId())
//                .content(comment.getContent())
//                .diffCreatedAt(DateUtil.diffDate(comment.getCreatedAt()))
//                .nickname(findMember.getNickname())
//                .city(findMember.getArea().getAreaName())
//                .provider(findMember.getProvider())
//                .likeInpYn(likeInpYn)
//                .build();
//    }
//
//    boolean containsMember(List<Likes> likesList, Long memberId) {
//        return likesList.stream().anyMatch(likes -> memberId.equals(likes.getMember().getId()));
//    }

}
