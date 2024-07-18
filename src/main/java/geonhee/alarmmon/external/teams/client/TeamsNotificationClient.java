package geonhee.alarmmon.external.teams.client;

import geonhee.alarmmon.external.teams.dto.TeamsNotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    value = "teams-notification-client",
    url = "${teams.notification.url}"
)
public interface TeamsNotificationClient {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    void sendNotification(@RequestBody TeamsNotificationRequest request);
}
