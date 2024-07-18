package geonhee.alarmmon.crossfit.controller;

import geonhee.alarmmon.crossfit.service.CrossfitSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/crossfit")
@RestController
public class CrossfitController {

    private CrossfitSchedulerService crossfitSchedulerService;

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
