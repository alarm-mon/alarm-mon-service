package geonhee.alarmmon.crossfit.controller;

import geonhee.alarmmon.crossfit.constant.Box;
import geonhee.alarmmon.crossfit.dtos.KakaoRequest;
import geonhee.alarmmon.crossfit.dtos.WodResponse;
import geonhee.alarmmon.crossfit.dtos.vo.WodBotVO;
import geonhee.alarmmon.crossfit.service.CrossfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/crossfit")
@RestController
public class CrossfitController {

    private final CrossfitService crossfitService;

//    @GetMapping("/wod")
//    public ResponseEntity<String[]> getWOD() {
//        return new ResponseEntity<>(crossfitService.getWODTexts(), HttpStatus.OK);
//    }
//
//    @PostMapping("/termination/noti")
//    public ResponseEntity<Void> sendServiceTerminationNotification() {
//        crossfitService.sendServiceTermination();
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PostMapping("/wod/noti")
//    public ResponseEntity<Void> sandNotificationWOD() {
//        crossfitService.sendWOD();
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/wod/noti/week")
//    public ResponseEntity<String[]> getNotificationSchedule() {
//        return new ResponseEntity<>(crossfitService.getWODTexts(), HttpStatus.OK);
//    }

    @GetMapping("/daily/wod/{box}")
    public ResponseEntity<WodResponse> getDailyWod(@PathVariable Box box) throws InterruptedException {
        WodResponse wod = crossfitService.sendWOD(box);
        return ResponseEntity.ok(wod);
    }

    @PostMapping("/daily/wod/kakao")
    public ResponseEntity<WodResponse> getDailyWod(@RequestBody KakaoRequest<WodBotVO> request) throws InterruptedException {
        String boxCode = request.getAction().getParams().getBoxCode();
        WodResponse wod = crossfitService.sendWOD(Box.valueOf(boxCode));
        return ResponseEntity.ok(wod);
    }
}
