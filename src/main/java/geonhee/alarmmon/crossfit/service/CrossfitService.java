package geonhee.alarmmon.crossfit.service;

import geonhee.alarmmon.crossfit.constant.BoxNameCode;
import geonhee.alarmmon.crossfit.dtos.WodResponse;
import geonhee.alarmmon.external.teams.service.NotificationService;
import geonhee.alarmmon.external.teams.dto.TeamsNotificationRequest.Body;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrossfitService {

    private final NotificationService notificationService;
    private static final String searchUri = "https://search.naver.com/search.naver?ssc=tab.nx.all&where=nexearch&sm=tab_dgs&qdt=0&query=";

    public List<String> titles = new ArrayList<>();

    public WodResponse sendWOD(String id) throws InterruptedException {
        WodResponse res = new WodResponse();
        BoxNameCode boxNameCode = BoxNameCode.findByBoxCode(id);

        // 1. title 조회
        String title = getWODTitle(boxNameCode.getBoxUri());
        if (titles.contains(title)) {
            return res;
        }

        // 2. 네이버 검색으로 와드 추출
        List<String> wodTexts = getWODTextFromSearch(title);
        res.setWodTexts(wodTexts);

        // 3. 팀즈로 알림 전송
        sendNotification(wodTexts);

        titles.add(title);
        return res;
    }

    protected String getWODTitle(String uri) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless"); // background 실행 옵션 추가
        ChromeDriver webDriver = new ChromeDriver(options);

        try {
            webDriver.get(uri);
            WebDriver iframe = webDriver.switchTo().frame(webDriver.findElement(By.name("cafe_main")));
            WebElement element = iframe.findElements(By.className("article-board")).get(1).findElement(By.tagName("a"));
            String title = element.getText().trim();
            System.out.println("title : " + title);
            return title;
        } finally {
            webDriver.quit();
        }
    }


    protected List<String> getWODTextFromSearch(String title) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless"); // background 실행 옵션 추가
        ChromeDriver webDriver = new ChromeDriver(options);
        String uri = searchUri + "\"" + title + "\"";
        try {
            webDriver.get(uri);

            WebElement element = webDriver.findElement(By.className("detail_box")).findElement(By.tagName("a"));
            String href = element.getAttribute("href");
            webDriver.get(href);

            // 페이지 뜨는 시간 기다리기
            Thread.sleep(2000);

            WebDriver newIframe = webDriver.switchTo().frame(webDriver.findElement(By.name("cafe_main")));
            List<WebElement> elements = newIframe.findElements(By.className("se-text-paragraph"));
            List<String> text = new ArrayList<>();
            for (WebElement webElement : elements) {
                System.out.println(webElement.getText());
                text.add(webElement.getText());
            }

            System.out.println("wodTexts : " + text);
            return text;
        } finally {
            webDriver.quit();
        }
    }

    private void sendNotification(List<String> wodTexts) {
        Body date = Body.builder()
                .text(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
                .weight("Bolder")
                .horizontalAlignment("Center")
                .build();
        Body content = new Body(String.join("\n\n", wodTexts));
        notificationService.send("오늘의 WOD 입니다. 삐빅-", date, content);
    }

}
