package geonhee.alarmmon.crossfit.service;

import geonhee.alarmmon.external.teams.dto.TeamsNotificationRequest.Body;
import geonhee.alarmmon.external.teams.service.NotificationService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrossfitService {

    private static final String WOD_URL = "https://cafe.naver.com/ArticleList.nhn?search.clubid=27882730&search.menuid=9&search.boardtype=L";

    private final NotificationService notificationService;

    public void sendWOD() {
        Body date = Body.builder()
            .text(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
            .weight("Bolder")
            .horizontalAlignment("Center")
            .build();
        Body content = new Body(getWODText());

        notificationService.send("오늘의 WOD 입니다. 삐빅-", date, content);
    }

    public String[] getWODTexts() {
        return getWODText().split("\n\n");
    }

    private String getWODText() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless"); // background 실행 옵션 추가
        ChromeDriver webDriver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        try {
            webDriver.get(WOD_URL);
            WebDriver iframe = webDriver.switchTo().frame(webDriver.findElement(By.name("cafe_main")));
            WebElement element = iframe.findElements(By.className("article-board")).get(1).findElement(By.tagName("a"));
            webDriver.get(element.getAttribute("href"));

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("cafe_main")));
            WebDriver newIframe = webDriver.switchTo().frame(webDriver.findElement(By.name("cafe_main")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("se-component-content")));
            List<WebElement> elements = newIframe.findElements(By.className("se-component-content"));
            String text = "";
            for (WebElement webElement : elements) {
                text += webElement.getText();
                text += "\n";
            }
            return text.replace("\n", "\n\n");
        } finally {
            webDriver.close();
        }
    }
}
