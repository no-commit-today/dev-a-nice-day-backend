package com.nocommittoday.techswipe.client.file.dev;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.client.file.FileClient;
import com.nocommittoday.techswipe.client.file.FileResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("develop")
class FileClientDevTest {

    private FileClient fileClient = new FileClient(RestClient.builder());

    @Test
    void 리다이렉트필요한것() {
        // given
        // when
        final ClientResponse<FileResponse> response = fileClient.get("http://thefarmersfront.github.io/post-img/road-to-ddd/library.png");

        // then
        System.out.println(response.getData());
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.get().contentType()).isEqualTo("image/png");

        response.get().file().delete();
    }

    @Test
    void UrlResource_로_inputStream_하면_400에러_뜨던거() {
        // given
        // when
        final ClientResponse<FileResponse> response = fileClient.get("https://tech.socarcorp.kr/img/fms-trip-event-pipeline/교차도록 배경이미지.jpg");

        // then
        System.out.println(response.getData());
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.get().contentType()).isEqualTo("image/jpeg");
        response.get().file().delete();
    }

    @Test
    void 에러_403_뜨던거() {
        // given
        // when
        final ClientResponse<FileResponse> response = fileClient.get("https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FGn8Jg%2FbtsjR7s561f%2F0vBashdooUnSomPpKv0pCk%2Fimg.png");

        // then
        System.out.println(response.getData());
        assertThat(response.isSuccess()).isTrue();
        response.get().file().delete();
    }

    @Test
    void 에러_403_뜨던거2() {
        // given
        // when
        final ClientResponse<FileResponse> response = fileClient.get("https://static.toss.im/career-resource/techblog_slash23_og_06_김동석.png");

        // then
        System.out.println(response.getData());
        assertThat(response.isSuccess()).isTrue();
        response.get().file().delete();
    }
}