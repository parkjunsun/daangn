package js.daangnclone.service.like;


import js.daangnclone.Exception.CustomException;
import js.daangnclone.Exception.ErrorCode;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.domain.comment.CommentRepository;
import js.daangnclone.domain.like.Likes;
import js.daangnclone.domain.like.LikesRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static js.daangnclone.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesServiceImpl implements LikesService {

    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likeRepository;

    @Override
    @Transactional
    public void processLike(Long memberId, Long commentId) {
        Optional<Likes> findLike = likeRepository.findByMemberIdAndCommentId(memberId, commentId);
        if (findLike.isEmpty()) {
            createLike(memberId, commentId);
        } else {
            removeLike(memberId, commentId);
        }
    }


    private void createLike(Long memberId, Long commentId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Comment findComment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

        Likes like = new Likes();
        like.setMember(findMember);
        like.setComment(findComment);

        likeRepository.save(like);
    }

    private void removeLike(Long memberId, Long commentId) {
        Optional<Likes> findLike = likeRepository.findByMemberIdAndCommentId(memberId, commentId);
        findLike.ifPresent(likes -> likeRepository.delete(likes));
    }

    @Override
    public String getInpLikeYn(Long memberId, Long commentId) {
        Optional<Likes> findLike = likeRepository.findByMemberIdAndCommentId(memberId, commentId);

        String likeInpYn = null;

        if (findLike.isPresent()) {
            likeInpYn = "Y";
        } else {
            likeInpYn = "N";
        }

        return likeInpYn;
    }

    @Override
    public long countLikesInComment(Comment comment) {
        return likeRepository.countByComment(comment);
    }
}