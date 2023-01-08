package js.daangnclone.service.keyword;

import js.daangnclone.exception.CustomException;
import js.daangnclone.domain.keyword.Keyword;
import js.daangnclone.domain.keyword.KeywordRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.keyword.dto.KeywordForm;
import js.daangnclone.web.keyword.dto.KeywordResponse;
import js.daangnclone.web.keyword.dto.KeywordEventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static js.daangnclone.exception.ErrorCode.KEYWORD_NOT_FOUND;
import static js.daangnclone.exception.ErrorCode.OVERSIZE_KEYWORD;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordServiceImpl implements KeywordService{

    private final KeywordRepository keywordRepository;

    @Override
    @Transactional
    public Keyword registerKeyword(KeywordForm keywordForm, Member member) {
        Keyword keyword = Keyword.builder()
                .word(keywordForm.getKeyword())
                .member(member)
                .build();

        return keywordRepository.save(keyword);
    }

    @Override
    public List<KeywordResponse> inquireKeywordList(Member member) {
        List<Keyword> keywordList = keywordRepository.findByMember(member);

        return keywordList.stream()
                .map(keyword -> KeywordResponse.builder()
                        .id(keyword.getId())
                        .word(keyword.getWord())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<KeywordEventResponse> inquireAllKeywordList() {
        List<Keyword> allKeywordList = keywordRepository.findAll();

        return allKeywordList.stream()
                .map(keyword -> KeywordEventResponse.builder()
                        .id(keyword.getId())
                        .word(keyword.getWord())
                        .member(keyword.getMember())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteKeyword(Long keywordId) {
        Keyword keyword = keywordRepository.findById(keywordId).orElseThrow(() -> new CustomException(KEYWORD_NOT_FOUND));
        keywordRepository.delete(keyword);
    }

    @Override
    public void validateKeywordMaxCnt(Member member) {
        long numberOfRegisteredKeyword = keywordRepository.countByMember(member);

        if (numberOfRegisteredKeyword >= 30) {
            throw new CustomException(OVERSIZE_KEYWORD);
        }
    }
}
