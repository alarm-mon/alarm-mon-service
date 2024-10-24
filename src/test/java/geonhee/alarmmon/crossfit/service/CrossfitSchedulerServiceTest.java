package geonhee.alarmmon.crossfit.service;

import geonhee.alarmmon.crossfit.constant.Box;
import geonhee.alarmmon.crossfit.dtos.WodResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;


@SpringBootTest
public class CrossfitSchedulerServiceTest {
    @Autowired
    private CrossfitService crossfitSchedulerService;

    @Test
    @Disabled
    void sendWODTest() throws Exception {
        // given
        Box box = Box.CROSSFIT_MATE_SUNAE;

        // when
        WodResponse wod = crossfitSchedulerService.sendWOD(box);

        // then
        System.out.println(wod.getWodTexts());
    }

    @Test
    void getWODTextTest() throws Exception {
        // given
        String uri = Box.CROSSFIT_MATE_SUNAE.getUrl();

        // when
        String title = crossfitSchedulerService.getWODTitle(uri);

        // then
        System.out.println(title);
    }

    @Test
    void getWODTextFromSearchTest() throws Exception {
        // given
        String title = "2024.10.23 <EMOM>"; // 메이트 수내점
//        String title = ""; // 졸리 시흥
//        String title = ""; // 테디짐

        // when
        crossfitSchedulerService.getWODTextFromSearch(title);

        // then
    }
}

