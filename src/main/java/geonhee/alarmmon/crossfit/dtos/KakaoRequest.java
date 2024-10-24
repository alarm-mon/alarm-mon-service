package geonhee.alarmmon.crossfit.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoRequest<T> {
    private Action<T> action;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Action<T> {
        private String id;
        private String name;
        private T params;
    }
}
