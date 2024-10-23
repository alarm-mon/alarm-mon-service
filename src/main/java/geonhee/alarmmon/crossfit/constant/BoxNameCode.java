package geonhee.alarmmon.crossfit.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoxNameCode {
    CROSSFIT_MATE_SUNAE("1","크로스핏 메이트 수내", "https://cafe.naver.com/ArticleList.nhn?search.clubid=28739580&search.menuid=19&search.boardtype=L"),
    CROSSFIT_JOLLY_SIHEUNG("2", "크로스핏 졸리 시흥", "https://cafe.naver.com/ArticleList.nhn?search.clubid=31077105&search.menuid=12&search.boardtype=L"),
    CROSSFIT_TEDDYGYM("3", "크로스핏 테디짐", "https://cafe.naver.com/ArticleList.nhn?search.clubid=27882730&search.menuid=9&search.boardtype=L");

    private final String boxCode;
    private final String boxName;
    private final String boxUri;

    public static BoxNameCode findByBoxCode(String boxCode) {
        for (BoxNameCode value : values()) {
            if (value.getBoxCode().equals(boxCode)) {
                return value;
            }
        }
        return null;
    }

}
