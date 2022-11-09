package js.daangnclone.domain.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchType {

    ALL("01", "ALL"),
    KEYWORD("02", "KEYWORD"),
    CATEGORY("03", "CATEGORY");

    private final String key;
    private final String value;
}
