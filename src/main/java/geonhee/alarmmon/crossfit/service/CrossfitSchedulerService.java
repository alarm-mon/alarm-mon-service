package geonhee.alarmmon.crossfit.service;

import geonhee.alarmmon.crossfit.repository.CrossfitRepository;
import geonhee.alarmmon.external.teams.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CrossfitSchedulerService {

    private static final String WOD_URL = "https://cafe.naver.com/ArticleList.nhn?search.clubid=27882730&search.menuid=9&search.boardtype=L";

    private final NotificationService notificationService;
    private final CrossfitRepository crossfitRepository;

    /**
     * 월요일, 목요일 오전 8시 와드 정보 알람 전송
     */
//    @Scheduled(fixedDelay = 1000 * 20)
    @Scheduled(cron = "0 0 8 * * MON,THU")
    public void sendWOD() throws InterruptedException {
        String text = getWODText();

        System.out.println(text);

//        notificationService.send(text);
    }

    private String getWODText() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless"); // background 실행 옵션 추가
        ChromeDriver webDriver = new ChromeDriver(options);

        try {
            webDriver.get(WOD_URL);
            WebDriver iframe = webDriver.switchTo().frame(webDriver.findElement(By.name("cafe_main")));
            WebElement element = iframe.findElements(By.className("article-board")).get(1).findElement(By.tagName("a"));
            String title = element.getText();
            validateNewPost(title);
            crossfitRepository.updateTitle(title);
            String href = element.getAttribute("href");
            webDriver.get(href);

            // 페이지 뜨는 시간 기다리기
            Thread.sleep(5000);

            WebDriver newIframe = webDriver.switchTo().frame(webDriver.findElement(By.name("cafe_main")));
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

    private void validateNewPost(String title) {
        if (crossfitRepository.isSameTitle(title)) {
            throw new IllegalArgumentException("신규 게시글 없음");
        }
    }
}
