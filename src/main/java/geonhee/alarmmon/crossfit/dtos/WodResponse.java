package geonhee.alarmmon.crossfit.dtos;

import lombok.Data;
import lombok.Getter;

import java.util.List;


@Getter
public class WodResponse {
    private List<String> wodTexts;

    public void setWodTexts(List<String> wodTexts) {
        this.wodTexts = wodTexts;
    }
}
