package js.daangnclone.domain.area;

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
public class Area {
    @Id
    private Long areaCd;
    private String areaName;

    private Long pprAreaCd;

}
