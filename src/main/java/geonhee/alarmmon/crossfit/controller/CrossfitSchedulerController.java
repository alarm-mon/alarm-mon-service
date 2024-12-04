package geonhee.alarmmon.crossfit.controller;

import geonhee.alarmmon.crossfit.constant.Box;
import geonhee.alarmmon.crossfit.service.CrossfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CrossfitSchedulerController {

    private final CrossfitService crossfitService;

    // 2024.12.04 - 2025.01.05 홀딩으로 알람 임시 제거
    // @Scheduled(cron = "0 0 8 * * MON,WED,THU")
    public void sendWODNotificationMateSunae() throws InterruptedException {
        crossfitService.sendWOD(Box.CROSSFIT_MATE_SUNAE);
    }


    @Scheduled(cron = "0 5 8 * * ?")
    public void sendWODNotificationJolly() throws InterruptedException {
        crossfitService.sendWOD(Box.CROSSFIT_JOLLY_SIHEUNG);
    }
}
