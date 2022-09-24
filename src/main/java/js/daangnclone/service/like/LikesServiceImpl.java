package js.daangnclone.service.like;


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
        Member findMember = memberRepository.findById(memberId).orElse(null);
        Comment findComment = commentRepository.findById(commentId).orElse(null);

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
}
