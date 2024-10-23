package geonhee.alarmmon.external.teams.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TeamsNotificationRequest {

    private final String type;
    private final List<Attachment> attachments;

    public TeamsNotificationRequest(String title, List<Body> texts) {
        this.type = "message";
        attachments = List.of(new Attachment(title, texts));
    }

    @Getter
    public static class Attachment {

        private final String contentType;
        private final Content content;
        private String contentUrl;

        public Attachment(String title, List<Body> texts) {
            this.contentType = "application/vnd.microsoft.card.adaptive";
            this.content = new Content(title, texts);
            this.contentUrl = null;
        }
    }

    @Getter
    public static class Content {

        @JsonProperty("$schema")
        private final String schema;
        private final String type;
        private final String version;
        private final MsTeams msteams;
        @JsonProperty("body")
        private List<Body> bodies;

        public Content(String title, List<Body> texts) {
            this.schema = "http://adaptivecards.io/schemas/adaptive-card.json";
            this.type = "AdaptiveCard";
            this.version = "1.2";
            this.msteams = new MsTeams("Full");
            bodies = new ArrayList<>();
            bodies.add(Body.createTitle(title));
            bodies.addAll(texts);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MsTeams {

        private String width;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {

        private final String type;
        private final String text;
        private String size;
        private String weight;
        private String horizontalAlignment;
        private String color;
        private String url;
        private boolean wrap;

        public Body(String text) {
            this(text, null, null, null, null, null);
        }

        @Builder
        public Body(String text, String size, String weight, String horizontalAlignment,
            String color, String url) {
            this.type = "TextBlock";
            this.text = text;
            this.size = size;
            this.weight = weight;
            this.horizontalAlignment = horizontalAlignment;
            this.color = color;
            this.wrap = true;
        }

        public static Body createTitle(String title) {
            return Body.builder()
                .text(title)
                .size("ExtraLarge")
                .weight("Bolder")
                .horizontalAlignment("Center")
                .color("Accent")
                .build();
        }

        public static Body createSubTitle(String subTitle) {
            return Body.builder()
                .text(subTitle)
                .weight("Bolder")
                .horizontalAlignment("Center")
                .build();
        }
    }
}
