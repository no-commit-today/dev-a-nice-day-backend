package com.nocommittoday.client.feed.dev;

import com.nocommittoday.client.core.ClientResponse;
import com.nocommittoday.client.feed.FeedClient;
import com.nocommittoday.client.feed.FeedClientConfig;
import com.nocommittoday.client.feed.FeedResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(FeedClientConfig.class)
@Tag("develop")
class FeedClientDevTest {

    @Autowired
    private FeedClient feedClient;

    @Test
    void 무신사() {
        final String url = "https://medium.com/feed/musinsa-tech";
        final ClientResponse<FeedResponse> response = feedClient.get(url);

        final FeedResponse feed = response.getData();
        System.out.println(feed);
        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 네이버_D2() {
        final String url = "https://d2.naver.com/d2.atom";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 마켓컬리() {
        final String url = "https://helloworld.kurly.com/feed.xml";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .describedAs("소개글이어서 사용 불가능")
                .isNotEmpty();
    }

    @Test
    void 우아한형제들() {
        final String url = "https://techblog.woowahan.com/feed";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 카카오엔터프라이즈() {
        final String url = "https://tech.kakaoenterprise.com/feed";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 데브시스터즈() {
        final String url = "https://tech.devsisters.com/rss.xml";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 라인() {
        final String url = "https://techblog.lycorp.co.jp/ko/feed/index.xml";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .describedAs("요약이어서 사용 불가능")
                .isNotEmpty();
    }

    @Test
    void 쿠팡() {
        final String url = "https://medium.com/feed/coupang-engineering";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 당근마켓() {
        final String url = "https://medium.com/feed/daangn";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 토스() {
        final String url = "https://toss.tech/rss.xml";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 직방() {
        final String url = "https://medium.com/feed/zigbang";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 왓챠() {
        final String url = "https://medium.com/feed/watcha";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 뱅크샐러드() {
        final String url = "https://blog.banksalad.com/rss.xml";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .describedAs("요약이어서 사용 불가능")
                .isNotEmpty();
    }

    @Test
    void 하이퍼커넥트() {
        final String url = "https://hyperconnect.github.io/feed.xml";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 요기요() {
        final String url = "https://techblog.yogiyo.co.kr/feed";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 쏘카() {
        final String url = "https://tech.socarcorp.kr/feed";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 리디() {
        final String url = "https://www.ridicorp.com/feed";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void NHN_Toast() {
        final String url = "https://meetup.toast.com/rss";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

}
