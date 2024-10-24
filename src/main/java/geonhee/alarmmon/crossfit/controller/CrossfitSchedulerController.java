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

    @Scheduled(cron = "0 0 8 * * MON-FRI")
    public void sendWODNotificationMateSunae() throws Exception {
        crossfitService.sendWOD(Box.CROSSFIT_MATE_SUNAE);
    }


    @Scheduled(cron = "0 5 8 * * ?")
    public void sendWODNotificationJolly() throws Exception {
        crossfitService.sendWOD(Box.CROSSFIT_JOLLY_SIHEUNG);
    }
}
