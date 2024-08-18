package com.nocommittoday.techswipe.client.feed.dev;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.client.feed.FeedClient;
import com.nocommittoday.techswipe.client.feed.FeedResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("develop")
class FeedClientDevTest {

    @Autowired
    private FeedClient feedClient;

    @Test
    void 무신사() {
        String url = "https://medium.com/feed/musinsa-tech";
        ClientResponse<FeedResponse> response = feedClient.get(url);

        FeedResponse feed = response.getData();
        System.out.println(feed);
        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 네이버_D2() {
        String url = "https://d2.naver.com/d2.atom";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 마켓컬리() {
        String url = "https://helloworld.kurly.com/feed.xml";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .describedAs("소개글이어서 사용 불가능")
                .isNotEmpty();
    }

    @Test
    void 우아한형제들() {
        String url = "https://techblog.woowahan.com/feed";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 카카오엔터프라이즈() {
        String url = "https://tech.kakaoenterprise.com/feed";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 데브시스터즈() {
        String url = "https://tech.devsisters.com/rss.xml";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 라인() {
        String url = "https://techblog.lycorp.co.jp/ko/feed/index.xml";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .describedAs("요약이어서 사용 불가능")
                .isNotEmpty();
    }

    @Test
    void 쿠팡() {
        String url = "https://medium.com/feed/coupang-engineering";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 당근마켓() {
        String url = "https://medium.com/feed/daangn";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 토스() {
        String url = "https://toss.tech/rss.xml";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 직방() {
        String url = "https://medium.com/feed/zigbang";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 왓챠() {
        String url = "https://medium.com/feed/watcha";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 뱅크샐러드() {
        String url = "https://blog.banksalad.com/rss.xml";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content())
                .describedAs("요약이어서 사용 불가능")
                .isNotEmpty();
    }

    @Test
    void 하이퍼커넥트() {
        String url = "https://hyperconnect.github.io/feed.xml";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 요기요() {
        String url = "https://techblog.yogiyo.co.kr/feed";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 쏘카() {
        String url = "https://tech.socarcorp.kr/feed";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void 리디() {
        String url = "https://www.ridicorp.com/feed";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void NHN_Toast() {
        String url = "https://meetup.toast.com/rss";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
        assertThat(entry.link()).isNotEmpty();
        assertThat(entry.title()).isNotEmpty();
        assertThat(entry.date()).isNotNull();
        assertThat(entry.content()).isNotEmpty();
    }

    @Test
    void _29CM() {
        String url = "https://medium.com/feed/29cm";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println(feed);

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://medium.com/feed/naver-place-dev";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://netmarble.engineering/feed/";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://danawalab.github.io/feed";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://medium.com/feed/bunjang-tech-blog";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
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

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://medium.com/feed/yanoljacloud-tech";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
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

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://medium.com/feed/wantedjobs";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
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

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://fe-developers.kakaoent.com/rss.xml";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://fe-developers.kakaoent.com/rss.xml";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://medium.com/feed/tabling-tech";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
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

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://zuminternet.github.io/feed.xml";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl())
                .isNull();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://dev.gmarket.com/rss";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
        System.out.println("link: " + feed.link());
        System.out.println("title: " + feed.title());
        System.out.println("iconUrl: " + feed.iconUrl());

        assertThat(feed).isNotNull();
        assertThat(feed.title()).isNotEmpty();
        assertThat(feed.link()).isNotEmpty();
        assertThat(feed.iconUrl()).isNotEmpty();
        assertThat(feed.entries()).isNotEmpty();

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://medium.com/feed/naverfinancial";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
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

        FeedResponse.Entry entry = feed.entries().get(0);
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
        String url = "https://medium.com/feed/naverfinancial";
        ClientResponse<FeedResponse> response = feedClient.get(url);
        FeedResponse feed = response.getData();
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

        FeedResponse.Entry entry = feed.entries().get(0);
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
