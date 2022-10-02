package js.daangnclone.domain.like;

import js.daangnclone.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("select l from Likes l where l.member.id = :memberId and l.comment.id = :commentId")
    Optional<Likes> findByMemberIdAndCommentId(@Param("memberId") Long memberId, @Param("commentId")Long commentId);

    Long countByComment(Comment comment);

}
