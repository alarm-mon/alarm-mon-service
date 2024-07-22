package geonhee.alarmmon.crossfit.controller;

import geonhee.alarmmon.crossfit.service.CrossfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/crossfit")
@RestController
public class CrossfitController {

    private final CrossfitService crossfitService;

    @GetMapping("/wod")
    public ResponseEntity<String[]> getWOD() {
        return new ResponseEntity<>(crossfitService.getWODTexts(), HttpStatus.OK);
    }
}
