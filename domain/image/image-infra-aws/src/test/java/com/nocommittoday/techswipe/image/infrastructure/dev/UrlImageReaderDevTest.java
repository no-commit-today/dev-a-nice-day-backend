package com.nocommittoday.techswipe.image.infrastructure.dev;

import com.nocommittoday.techswipe.image.infrastructure.ImageData;
import com.nocommittoday.techswipe.image.infrastructure.UrlImageReader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Tag("develop")
@SpringBootTest
@Disabled
class UrlImageReaderDevTest {

    @Autowired
    private UrlImageReader urlImageReader;

    @Test
    void 우아한형제들_아이콘() {
        final ImageData imageData = urlImageReader.get("https://techblog.woowahan.com/wp-content/uploads/2020/08/favicon.ico");
        System.out.println(imageData.contentLength());
        System.out.println(imageData.contentType());
    }

    @Test
    void 커넥션_타임아웃() {
        final ImageData imageData = urlImageReader.get("https://techblog.woowa.in/wp-content/uploads/2024/06/%EB%B8%94%EB%A1%9C%EA%B7%B8-%EC%8B%A4%EC%8B%9C%EA%B0%84-%EB%B0%98%EC%9D%91%ED%98%95-%EC%B6%94%EC%B2%9C-%EA%B0%9C%EB%B0%9C-%EC%9D%BC%EC%A7%80-1.-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%86%8C%EA%B0%9C-%EC%84%AC%EB%84%A4%EC%9D%BC.png");
        System.out.println(imageData.contentLength());
        System.out.println(imageData.contentType());
        try (final var inputStream = imageData.getInputStream()) {
            System.out.println(inputStream.readAllBytes().length);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void 에러_403_url_인코딩관련() {
        final ImageData imageData = urlImageReader.get("https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FGn8Jg%2FbtsjR7s561f%2F0vBashdooUnSomPpKv0pCk%2Fimg.png");
        System.out.println(imageData.contentLength());
        System.out.println(imageData.contentType());
        try (final var inputStream = imageData.getInputStream()) {
            System.out.println(inputStream.readAllBytes().length);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void 에러_400() {
        final ImageData imageData = urlImageReader.get("https://tech.socarcorp.kr/img/fms-trip-event-pipeline/교차도록 배경이미지.jpg");
        System.out.println(imageData.contentLength());
        System.out.println(imageData.contentType());
        try (final var inputStream = imageData.getInputStream()) {
            System.out.println(inputStream.readAllBytes().length);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void 에러400_요청해보기() throws IOException {
        URL url = new URL("https://tech.socarcorp.kr/img/fms-trip-event-pipeline/교차도록 배경이미지.jpg");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

        InputStream inputStream = urlConnection.getInputStream();
    }

    @Test
    void 멈춤() {
        final ImageData imageData = urlImageReader.get("http://thefarmersfront.github.io/post-img/road-to-ddd/library.png");
        System.out.println(imageData.contentLength());
        System.out.println(imageData.contentType());
        try (final var inputStream = imageData.getInputStream()) {
            System.out.println(inputStream.readAllBytes().length);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void 에러_403() {
        final ImageData imageData = urlImageReader.get("https://static.toss.im/career-resource/techblog_slash23_og_06_김동석.png");
        System.out.println(imageData.contentLength());
        System.out.println(imageData.contentType());
        try (final var inputStream = imageData.getInputStream()) {
            System.out.println(inputStream.readAllBytes().length);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    void 에러400뜬거_요청해보기() throws IOException {
//        final RestClient restClient = RestClient.builder().build();
//        final byte[] body = restClient.get()
//                .uri("https://tech.socarcorp.kr/img/fms-trip-event-pipeline/교차도록 배경이미지.jpg")
//                .retrieve()
//                .toEntity(byte[].class)
//                .getBody();

        final UrlResource resource = UrlResource.from("https://tech.socarcorp.kr/img/fms-trip-event-pipeline/교차도록 배경이미지.jpg");
        resource.getInputStream();
    }

    @Test
    void 멈춤뜬거_요청해보기() {
        final RestClient restClient = RestClient.builder().build();
        final ResponseEntity<byte[]> entity = restClient.get()
                .uri("http://thefarmersfront.github.io/post-img/road-to-ddd/library.png")
                .retrieve()
                .toEntity(byte[].class);
        System.out.println(entity);
    }

    @Test
    void 머엄추움() {
        final RestTemplate restTemplate = new RestTemplateBuilder().build();
        final ResponseEntity<byte[]> forEntity = restTemplate.getForEntity("http://thefarmersfront.github.io/post-img/road-to-ddd/library.png", byte[].class);
        System.out.println(forEntity);
    }

    @Test
    void 멈춤뜬거_요청해보기1_2() {
        final RestClient restClient = RestClient.builder().build();
        final ResponseEntity<byte[]> entity = restClient.get()
                .uri("https://helloworld.kurly.com/post-img/road-to-ddd/library.png")
                .retrieve()
                .toEntity(byte[].class);
        System.out.println(entity);
    }
}