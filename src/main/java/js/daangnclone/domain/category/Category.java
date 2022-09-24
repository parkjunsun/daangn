package js.daangnclone.domain.category;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Category extends TimeEntity {

    @Id
    private Long categoryCd;
    private String categoryName;

    @OneToOne(mappedBy = "category", fetch = FetchType.LAZY)
    private Board board;

    public Category(Long categoryCd, String categoryName) {
        this.categoryCd = categoryCd;
        this.categoryName = categoryName;
    }
}
