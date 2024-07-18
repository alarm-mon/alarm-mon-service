package geonhee.alarmmon.external.teams.service;

import geonhee.alarmmon.external.teams.client.TeamsNotificationClient;
import geonhee.alarmmon.external.teams.dto.TeamsNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final TeamsNotificationClient client;

    public void send(String text) {
        client.sendNotification(new TeamsNotificationRequest(text));
    }
}
