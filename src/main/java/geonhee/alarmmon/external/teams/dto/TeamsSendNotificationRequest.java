package geonhee.alarmmon.external.teams.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamsSendNotificationRequest {

    private String title;
    private String contents;
}
