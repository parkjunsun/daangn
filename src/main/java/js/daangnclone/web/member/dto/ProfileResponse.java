package js.daangnclone.web.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ProfileResponse {

    private int reviewScore;
    private long numberOfSales;
    private long amountOfSales;
    private long numberOfPurchases;
    private long amountOfPurchases;

    @Builder
    public ProfileResponse(int reviewScore, long numberOfSales, long amountOfSales, long numberOfPurchases, long amountOfPurchases) {
        this.reviewScore = reviewScore;
        this.numberOfSales = numberOfSales;
        this.amountOfSales = amountOfSales;
        this.numberOfPurchases = numberOfPurchases;
        this.amountOfPurchases = amountOfPurchases;
    }

}
