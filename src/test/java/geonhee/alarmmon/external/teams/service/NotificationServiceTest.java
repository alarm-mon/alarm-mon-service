package geonhee.alarmmon.external.teams.service;

import geonhee.alarmmon.external.teams.client.TeamsNotificationClient;
import geonhee.alarmmon.external.teams.dto.TeamsNotificationRequest.Body;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService target;
    @Mock
    private TeamsNotificationClient client;

    @Test
    void sendNoti__thenSuccess() {
        // given
        Body[] bodies = new Body[] {new Body("test")};

        // when & then
        target.send("타이틀", bodies);
    }

    @Test
    void sendNotiWhenBodyIsNull__thenThrowException() {
        // given
        Body[] bodies = null;

        // when & then
        Assertions.assertThatThrownBy(() -> target.send("타이틀", bodies))
            .isInstanceOf(NullPointerException.class);
    }
}