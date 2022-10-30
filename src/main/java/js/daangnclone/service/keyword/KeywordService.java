package js.daangnclone.service.keyword;

import js.daangnclone.domain.keyword.Keyword;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.keyword.dto.KeywordForm;
import js.daangnclone.web.keyword.dto.KeywordResponse;

import java.util.List;

public interface KeywordService {

    Keyword registerKeyword(KeywordForm keywordForm, Member member);
    List<KeywordResponse> inquireKeywordList(Member member);
    void deleteKeyword(Long keywordId);
    void validateKeywordMaxCnt(Member member);
}
