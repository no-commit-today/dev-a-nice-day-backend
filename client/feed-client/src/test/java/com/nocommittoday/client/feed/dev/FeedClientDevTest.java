package com.nocommittoday.client.feed.dev;

import com.nocommittoday.techswipe.client.core.ClientResponse;
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

    @Test
    void _29CM() {
        final String url = "https://medium.com/feed/29cm";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("link: " + entry.link());
        System.out.println("title: " + entry.title());
        System.out.println("date: " + entry.date());
        System.out.println("content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 네이버_플레이스() {
        final String url = "https://medium.com/feed/naver-place-dev";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 넷마블() {
        final String url = "https://netmarble.engineering/feed/";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .describedAs("요약이어서 사용 불가능")
                .isNotEmpty();
    }

    @Test
    void 다나와() {
        final String url = "https://danawalab.github.io/feed";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .isNotEmpty();
    }

    @Test
    void 번개장터() {
        final String url = "https://medium.com/feed/bunjang-tech-blog";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl())
                .describedAs("미디엄 아이콘 사용 불가능")
                .isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .isNotEmpty();
    }

    @Test
    void 야놀자클라우드() {
        final String url = "https://medium.com/feed/yanoljacloud-tech";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl())
                .describedAs("미디엄 아이콘 사용 불가능")
                .isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .isNotEmpty();
    }

    @Test
    void 원티드() {
        final String url = "https://medium.com/feed/wantedjobs";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl())
                .describedAs("미디엄 아이콘 사용 불가능")
                .isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .isNotEmpty();
    }

    @Test
    void 카카오엔터테인먼트_FE() {
        final String url = "https://fe-developers.kakaoent.com/rss.xml";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .isNotEmpty();
    }

    @Test
    void 토스랩() {
        final String url = "https://fe-developers.kakaoent.com/rss.xml";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .isNotEmpty();
    }

    @Test
    void 테이블링() {
        final String url = "https://medium.com/feed/tabling-tech";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl())
                .describedAs("미디엄 아이콘 사용 불가능")
                .isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .isNotEmpty();
    }

    @Test
    void ZUM() {
        final String url = "https://zuminternet.github.io/feed.xml";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl())
                .isNull();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .describedAs("요약이어서 사용 불가능")
                .isNotEmpty();
    }

    @Test
    void 지마켓() {
        final String url = "https://dev.gmarket.com/rss";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .isNotEmpty();
    }

    @Test
    void 네이버파이낸셜() {
        final String url = "https://medium.com/feed/naverfinancial";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl())
                .describedAs("미디엄 아이콘 사용 불가능")
                .isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .isNotEmpty();
    }

    @Test
    void 여기어때() {
        final String url = "https://medium.com/feed/naverfinancial";
        final ClientResponse<FeedResponse> response = feedClient.get(url);
        final FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl())
                .describedAs("미디엄 아이콘 사용 불가능")
                .isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        final FeedResponse.Entry entry = feed.entries().get(0);
        System.out.println("entry.link: " + entry.link());
        System.out.println("entry.title: " + entry.title());
        System.out.println("entry.date: " + entry.date());
        System.out.println("entry.content: " + entry.content());
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .isNotEmpty();
    }
}
