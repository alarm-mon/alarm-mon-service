package geonhee.alarmmon.external.teams.controller;

import geonhee.alarmmon.external.teams.dto.TeamsNotificationRequest.Body;
import geonhee.alarmmon.external.teams.dto.TeamsSendNotificationRequest;
import geonhee.alarmmon.external.teams.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/teams")
public class TeamsNotificationController {

    private final NotificationService notificationService;

    @PostMapping("/noti")
    public void sendNotification(@RequestBody TeamsSendNotificationRequest request) {
        notificationService.send(request.getTitle(), new Body(request.getContents()));
    }
}
