package js.daangnclone.cmn.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static js.daangnclone.cmn.CmnCons.*;

@Getter
@RequiredArgsConstructor
public enum Category {

    DIGITAL_DEVICE(DIGITAL_DEVICE_CODE, DIGITAL_DEVICE_NAME),
    HOUSEHOLD_APPLIANCES(HOUSEHOLD_APPLIANCES_CODE, HOUSEHOLD_APPLIANCES_NAME),
    FURNITURE(FURNITURE_CODE, FURNITURE_NAME),
    LIVING_KITCHEN(LIVING_KITCHEN_CODE, LIVING_KITCHEN_NAME),
    CHILD_ITEM(CHILD_ITEM_CODE, CHILD_ITEM_NAME),
    CHILD_BOOKS(CHILD_BOOKS_CODE, CHILD_BOOKS_NAME),
    WOMEN_CLOTHING(WOMEN_CLOTHING_CODE, WOMEN_CLOTHING_NAME),
    MEN_CLOTHING(MEN_CLOTHING_CODE, MEN_CLOTHING_NAME),
    SPORT_LEISURE(SPORT_LEISURE_CODE, SPORT_LEISURE_NAME),
    BOOKS(BOOKS_CODE, BOOKS_NAME);

    private final Long categoryCd;
    private final String categoryName;

    public static Category of(Long categoryCd) {
        if (categoryCd == null) {
            throw new IllegalArgumentException();
        }

        for (Category c : Category.values()) {
            if (c.categoryCd.equals(categoryCd)) {
                return c;
            }
        }

        throw new IllegalArgumentException("일치하는 지역코드가 없습니다.");
    }
}
