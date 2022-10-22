package js.daangnclone.service.activity;

import js.daangnclone.domain.activity.Activity;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.activity.dto.ActivityResponse;

import java.util.List;

public interface ActivityService {

    Activity load(Activity activity);
    List<ActivityResponse> inquireActivityList(Member sender);
}
