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
    public void sendWODNotification() {
        crossfitService.sendWOD();
    }
}
