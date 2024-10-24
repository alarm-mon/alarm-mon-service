package geonhee.alarmmon.crossfit.service;

import geonhee.alarmmon.crossfit.constant.Box;
import geonhee.alarmmon.crossfit.dtos.WodResponse;
import geonhee.alarmmon.external.teams.dto.TeamsNotificationRequest.Body;
import geonhee.alarmmon.external.teams.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrossfitService {

    private final NotificationService notificationService;
    private static final String searchUri = "https://search.naver.com/search.naver?ssc=tab.nx.all&where=nexearch&sm=tab_dgs&qdt=0&query=";

    public List<String> titles = new ArrayList<>();

//    @Value("${spring.profiles.active}")
//    private String activeProfile;

    public WodResponse sendWOD(Box box) throws Exception {
        WodResponse response = new WodResponse();

        // 1. title 조회
        String title = getWODTitle(box.getUrl());
        if (titles.contains(title)) {
            return response;
        }

        // 2. 네이버 검색으로 와드 추출
        List<String> wodTexts = getWODTextFromSearch(title);
        response.setWodTexts(wodTexts);

        // 3. 팀즈로 알림 전송
        sendNotification(box.getName(), wodTexts);

        titles.add(title);
        return response;
    }

    protected String getWODTitle(String uri) throws Exception {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("headless"); // background 실행 옵션 추가
//        ChromeDriver webDriver = new ChromeDriver(options);
//
//        try {
//            webDriver.get(uri);
//            WebDriver iframe = webDriver.switchTo().frame(webDriver.findElement(By.name("cafe_main")));
//            WebElement element = iframe.findElements(By.className("article-board")).get(1).findElement(By.tagName("a"));
//            String title = element.getText().trim();
//            System.out.println("title : " + title);
//            return title;
//        } finally {
//            webDriver.quit();
//        }
//        if ("local".equals(activeProfile)) {
//            trustAllCertificates();
//        }

        try {
            Document doc = Jsoup.connect(uri).timeout(10000).get();

            Element iframe = doc.select(".article-board").get(1);
            Element firstLink = iframe.select("a").first();
            return firstLink.text();
        } catch(IOException e) {
            e.printStackTrace();
            throw new Exception("크로스핏 타이틀 조회 중 오류 발생");
        }
    }


    protected List<String> getWODTextFromSearch(String title) throws Exception {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("headless"); // background 실행 옵션 추가
//        ChromeDriver webDriver = new ChromeDriver(options);
//        String uri = searchUri + "\"" + title + "\"";
//        try {
//            webDriver.get(uri);
//
//            WebElement element = webDriver.findElement(By.className("detail_box")).findElement(By.tagName("a"));
//            String href = element.getAttribute("href");
//            webDriver.get(href);
//
//            // 페이지 뜨는 시간 기다리기
//            Thread.sleep(2000);
//
//            WebDriver newIframe = webDriver.switchTo().frame(webDriver.findElement(By.name("cafe_main")));
//            List<WebElement> elements = newIframe.findElements(By.className("se-text-paragraph"));
//            List<String> text = new ArrayList<>();
//            for (WebElement webElement : elements) {
//                System.out.println(webElement.getText());
//                text.add(webElement.getText());
//            }
//
//            System.out.println("wodTexts : " + text);
//            return text;
//        } finally {
//            webDriver.quit();
//        }
//        if ("local".equals(activeProfile)) {
//            trustAllCertificates();
//        }
        String uri = searchUri + "\"" + title + "\"";

        try {
            Document doc = Jsoup.connect(uri).timeout(10000).get();

            Element iframe = doc.selectFirst(".detail_box").selectFirst("a");
            String href = iframe.attr("href");
            doc = Jsoup.connect(href).timeout(10000).get();
            Elements elements = doc.select(".se-component-content").select("p");
            List<String> text = new ArrayList<>();
            for (Element element : elements) {
                System.out.println(element.text());
                text.add(element.text());
            }

            System.out.println("wodTexts : " + text);
            return text;
        } catch(IOException e) {
            e.printStackTrace();
            throw new Exception("크로스핏 wod 조회 중 오류 발생");
        }
    }

    private void sendNotification(String boxName, List<String> wodTexts) {
        Body box = Body.createSubTitle(boxName);
        Body date = Body.createSubTitle(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
        Body content = new Body(String.join("\n\n", wodTexts));

        notificationService.send("오늘의 WOD 입니다. 삐빅-", box, date, content);
    }

    private void trustAllCertificates() throws Exception {
        // 모든 인증서를 신뢰하도록 SSLContext 설정
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // 모든 호스트명을 신뢰하도록 설정
        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

}
