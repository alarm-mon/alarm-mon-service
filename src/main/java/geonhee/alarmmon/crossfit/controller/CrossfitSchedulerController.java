package geonhee.alarmmon.crossfit.controller;

import geonhee.alarmmon.crossfit.service.CrossfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CrossfitSchedulerController {

    private final CrossfitService crossfitService;

    @Scheduled(cron = "0 0 8 * * MON-FRI")
    public void sendWODNotificationMateSunae() throws InterruptedException {
        crossfitService.sendWOD("1");
    }


    @Scheduled(cron = "0 5 8 * * ?")
    public void sendWODNotificationJolly() throws InterruptedException {
        crossfitService.sendWOD("2");
    }
}
