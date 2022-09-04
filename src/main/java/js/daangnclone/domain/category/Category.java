package js.daangnclone.domain.category;

import js.daangnclone.domain.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends TimeEntity {

    @Id
    private Long categoryCd;
    private String categoryName;
}
