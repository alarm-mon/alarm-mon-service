package geonhee.alarmmon.external.teams.service;

import geonhee.alarmmon.external.teams.client.TeamsNotificationClient;
import geonhee.alarmmon.external.teams.dto.TeamsNotificationRequest;
import geonhee.alarmmon.external.teams.dto.TeamsNotificationRequest.Body;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final TeamsNotificationClient client;

    public void send(String title, Body... texts) {
        client.sendNotification(new TeamsNotificationRequest(title, List.of(texts)));
    }
}
