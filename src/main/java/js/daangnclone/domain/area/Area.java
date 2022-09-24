package js.daangnclone.domain.area;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.member.Member;
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
public class Area extends TimeEntity {
    @Id
    private Long areaCd;
    private String areaName;

    private Long pprAreaCd;

    @OneToOne(mappedBy = "area", fetch = FetchType.LAZY)
    private Member member;

    public Area(Long areaCd, String areaName, Long pprAreaCd) {
        this.areaCd = areaCd;
        this.areaName = areaName;
        this.pprAreaCd = pprAreaCd;
    }
}
