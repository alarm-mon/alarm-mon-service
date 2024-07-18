package geonhee.alarmmon.crossfit.repository;

import org.springframework.stereotype.Repository;

@Repository
public class CrossfitRepository {

    /**
     * 스케줄러로만 돌리는데 사용되는 값으로 동시성 처리 고려 x
     */
    private static String recentTitle = "";

    public boolean isSameTitle(String title) {
        return recentTitle.equals(title);
    }

    public void updateTitle(String title) {
        recentTitle = title;
    }
}
