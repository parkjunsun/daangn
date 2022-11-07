package js.daangnclone.cmn;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class DateUtil {

    public static final int SEC = 60;
    public static final int MIN = 60;
    public static final int HOUR = 24;
    public static final int DAY = 30;
    public static final int MONTH = 12;

//    private DateUtil() {
//        //인스턴스화를 막기 위함(이펙티브 자바 아이템4(인스턴스화를 막으려거든 private 생성자를 사용하라))
//    }

    public static String diffDate(LocalDateTime Date) {
        long curTime = System.currentTimeMillis();
        long regTime = Timestamp.valueOf(Date).getTime();
        long diffTime = (curTime - regTime) / 1000;

        String msg = null;

        if (diffTime < SEC) {
            msg = diffTime + "초 전";
        } else if ((diffTime /= SEC) < MIN) {
            msg = diffTime + "분 전";
        } else if ((diffTime /= MIN) < HOUR) {
            msg = diffTime + "시간 전";
        } else if ((diffTime /= HOUR) < DAY) {
            msg = diffTime + "일 전";
        } else if ((diffTime /= DAY) < MONTH) {
            msg = diffTime +"개월 전";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            String curYear = sdf.format(curTime);
            String passYear = sdf.format(regTime);
            int diffYear = Integer.parseInt(curYear) - Integer.parseInt(passYear);

            msg = diffYear + "년 전";
        }

        return msg;
    }
}
