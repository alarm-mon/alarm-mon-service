package geonhee.alarmmon.crossfit.service;

import geonhee.alarmmon.crossfit.constant.BoxNameCode;
import geonhee.alarmmon.crossfit.dtos.WodResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class CrossfitSchedulerServiceTest {
    @Autowired
    private CrossfitService crossfitSchedulerService;

    @Test
    void sendWODTest() throws InterruptedException {
        // given
        String id = "1";
        // when
        WodResponse wod = crossfitSchedulerService.sendWOD(id);
        // then
        System.out.println(wod.getWodTexts());
    }

    @Test
    void getWODTextTest() throws InterruptedException {
        // given
        BoxNameCode boxNameCode = BoxNameCode.CROSSFIT_MATE_SUNAE;
        String uri = boxNameCode.getBoxUri();
        // when
        String title = crossfitSchedulerService.getWODTitle(uri);
        // then
        System.out.println(title);
    }

    @Test
    void getWODTextFromSearchTest() throws InterruptedException {
        // given
        String title = "2024.10.23 <EMOM>"; // 메이트 수내점
//        String title = ""; // 졸리 시흥
//        String title = ""; // 테디짐
        // when
        crossfitSchedulerService.getWODTextFromSearch(title);
        // then
    }
}

