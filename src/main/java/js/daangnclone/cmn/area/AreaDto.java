package js.daangnclone.cmn.area;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AreaDto {

    private final Long areaCd;
    private final String areaName;

    @Builder
    public AreaDto(Long areaCd, String areaName) {
        this.areaCd = areaCd;
        this.areaName = areaName;
    }

}
