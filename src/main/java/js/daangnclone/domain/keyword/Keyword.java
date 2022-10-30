package js.daangnclone.domain.keyword;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    Long id;

    private String word;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Keyword(String word, Member member) {
        this.word = word;
        this.member = member;
    }

}
