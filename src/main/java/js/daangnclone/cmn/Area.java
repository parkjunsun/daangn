package js.daangnclone.cmn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.List;

import static js.daangnclone.cmn.CmnCons.*;

@Getter
//@RequiredArgsConstructor
public enum Area {


        SEOUL(SEOUL_CODE, SEOUL_NAME, null),
            GANGNAM(GANGNAM_CODE, GANGNAM_NAME, SEOUL),

        GYEONGGI(GYEONGGI_CODE, GYEONGGI_NAME, null),
            GAPYEONG(GAPYEONG_CODE, GANGNAM_NAME, GYEONGGI),
            GOYANG_DUKYANG(GOYANG_DUKYANG_CODE, GOYANG_DUKYANG_NAME, GYEONGGI),
            GOYANG_ILSANDONG(GOYANG_ILSANDONG_CODE, GOYANG_ILSANDONG_NAME, GYEONGGI),
            GOYANG_ILSANSU(GOYANG_ILSANSU_CODE, GOYANG_ILSANSU_NAME, GYEONGGI),
            GHACHUN(GHACHUN_CODE, GHACHUN_NAME, GYEONGGI),
            GHYANGMYONG(GHYANGMYONG_CODE, GHYANGMYONG_NAME, GYEONGGI),
            GHYANGCHU(GHYANGCHU_CODE, GHYANGCHU_NAME, GYEONGGI),
            GURI(GURI_CODE, GURI_NAME, GYEONGGI),
            GUNPO(GUNPO_CODE, GUNPO_NAME, GYEONGGI),
            GIMPO(GIMPO_CODE, GIMPO_NAME, GYEONGGI),
            NAMYANGCHU(NAMYANGCHU_CODE, NAMYANGCHU_NAME, GYEONGGI),
            DONGDUCHUN(DONGDUCHUN_CODE, DONGDUCHUN_NAME, GYEONGGI),
            BUCHUN_SOSA(BUCHUN_SOSA_CODE, BUCHUN_SOSA_NAME, GYEONGGI),
            BUCHUN_OJUNG(BUCHUN_OJUNG_CODE, BUCHUN_OJUNG_NAME, GYEONGGI),
            BUCHUN_ONEMI(BUCHUN_ONEMI_CODE, BUCHUN_ONEMI_NAME, GYEONGGI),
            SUNGNAM_BUNDANG(SUNGNAM_BUNDANG_CODE, SUNGNAM_BUNDANG_NAME, GYEONGGI),
            SUNGNAM_SUJUNG(SUNGNAM_SUJUNG_CODE, SUNGNAM_SUJUNG_NAME, GYEONGGI),
            SUNGNAM_CHUNGWON(SUNGNAME_CHUNGWON_CODE, SUNGNAME_CHUNGWON_NAME, GYEONGGI),
            SUWON_KOWNSUN(SUWON_KOWNSUN_CODE, SUWON_KOWNSUN_NAME, GYEONGGI),
            SUWON_YUNGTONG(SUNWON_YUNGTONG_CODE, SUNWON_YUNGTONG_NAME, GYEONGGI),
            SUWON_JANGAN(SUWON_JANGAN_CODE, SUWON_JANGAN_NAME, GYEONGGI),
            SUWON_PALDAL(SUWON_PALDAL_CODE, SUWON_PALDAL_NAME, GYEONGGI),
            SIHEUNG(SIHEUNG_CODE, SIHEUNG_NAME, GYEONGGI),
            ANSAN_DANWON(ANSAN_DANWON_CODE, ANSAN_DANWON_NAME, GYEONGGI),
            ANSAN_SANGROK(ANSAN_SANGROK_CODE, ANSAN_SANGROK_NAME, GYEONGGI),
            ANSUNG(ANSUNG_CODE, ANSUNG_NAME, GYEONGGI),
            ANYANG_DONGAN(ANYANG_DONGAN_CODE, ANYANG_DONGAN_NAME, GYEONGGI),
            ANYANG_MANAN(ANYANG_MANAN_CODE, ANYANG_MANAN_NAME, GYEONGGI),
            YANGCHU(YANGCHU_CODE, YANGCHU_NAME, GYEONGGI),
            YANGPYEONG(YANGPYEONG_CODE, YANGPYEONG_NAME, GYEONGGI),
            YEOJU(YEOJU_CODE, YEOJU_NAME, GYEONGGI),
            YEONCHUN(YEONCHUN_CODE, YEONCHUN_NAME, GYEONGGI),
            OSAN(OSAN_CODE, OSAN_NAME, GYEONGGI),
            YONGIN_KIHEUNG(YONGIN_KIHEUNG_CODE, YONGIN_KIHEUNG_NAME, GYEONGGI),
            YONGIN_SUJI(YONGIN_SUJI_CODE, YONGIN_SUJI_NAME, GYEONGGI),
            YONGIN_CHUIN(YONGIN_CHUIN_CODE, YONGIN_CHUIN_NAME, GYEONGGI),
            UIWANG(UIWANG_CODE, UIWANG_NAME, GYEONGGI),
            UIJUNGBU(UIJUNGBU_CODE, UIJUNGBU_NAME, GYEONGGI),
            ECHUN(ECHUN_CODE, ECHUN_NAME, GYEONGGI),
            PAJU(PAJU_CODE, PAJU_NAME, GYEONGGI),
            PYENGTAEK(PYENGTAEK_CODE, PYENGTAEK_NAME, GYEONGGI),
            POCHUN(POCHUN_CODE, POCHUN_NAME, GYEONGGI),
            HANAM(HANAM_CODE, HANAM_NAME, GYEONGGI),
            HWASUNG(HWASUNG_CODE, HWASUNG_NAME, GYEONGGI);


    private final Long areaCd;
    private final String areaName;
    private final Area parent;

    private final List<Area> children = new ArrayList<>();

    Area(Long areaCd, String areaName, Area parent) {
        this.areaCd = areaCd;
        this.areaName = areaName;
        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }

    private void addChild(Area child) {
        for (Area a = this; a != null; a = a.parent) {
            a.children.add(child);
        }
    }

    public List<Area> children() {
        return this.children;
    }


    public static Area of(Long areaCd) {
        if (areaCd == null) {
            throw new IllegalArgumentException();
        }

        for (Area a : Area.values()) {
            if (a.areaCd.equals(areaCd)) {
                return a;
            }
        }

        throw new IllegalArgumentException("일치하는 지역코드가 없습니다.");
    }

}
