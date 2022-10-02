package js.daangnclone.cmn;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryDto {

    private final Long categoryCd;
    private final String categoryName;

    @Builder
    public CategoryDto(Long categoryCd, String categoryName) {
        this.categoryCd = categoryCd;
        this.categoryName = categoryName;
    }

}
