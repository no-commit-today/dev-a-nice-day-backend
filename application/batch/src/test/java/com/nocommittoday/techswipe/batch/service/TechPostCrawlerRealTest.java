package com.nocommittoday.techswipe.batch.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("real")
class TechPostCrawlerRealTest {

    @Test
    void 데보션() {
        final TechPostCrawler techPostCrawler = new TechPostCrawler("https://devocean.sk.com/blog/techBoardDetail.do?ID=165867&boardType=techBlog&searchData=&page=&subIndex=");
        System.out.println("[이미지 URL]\n" + techPostCrawler.getImageUrl());
        System.out.println("[제목]\n" + techPostCrawler.getTitle(null));
        System.out.println("[날짜]\n" + techPostCrawler.getDate("div#wrapper > div.container > section.sub-sec.view.cont01 > div.sub-sec-inner > div.sub-view-title > div.view-info > div.view-author > span.date"));
        System.out.println("[본문]\n" + techPostCrawler.getContent("div#wrapper > div.container > section.sub-sec.view.cont01 > div.sub-sec-inner > div.sub-view-cont > div.toastui-editor-contents"));
    }

    @Test
    void AWS() {
        final TechPostCrawler techPostCrawler = new TechPostCrawler("https://aws.amazon.com/ko/blogs/tech/introduction-mtls-for-application-loadbalancer/");
        System.out.println("[이미지 URL]\n" + techPostCrawler.getImageUrl());
        System.out.println("[제목]\n" + techPostCrawler.getTitle("div#aws-page-content > div.aws-blog-content > main > article.blog-post > h1.blog-post-title"));
        System.out.println("[날짜]\n" + techPostCrawler.getDate("div#aws-page-content > div.aws-blog-content > main > article.blog-post > footer.blog-post-meta > time"));
        System.out.println("[본문]\n" + techPostCrawler.getContent("div#aws-page-content > div.aws-blog-content > main > article.blog-post > section.blog-post-content"));
    }

    @Test
    void 스포카() {
        final TechPostCrawler techPostCrawler = new TechPostCrawler("https://spoqa.github.io/2024/05/03/transfer-jdsl.html");
        System.out.println("[이미지 URL]\n" + techPostCrawler.getImageUrl());
        System.out.println("[제목]\n" + techPostCrawler.getTitle("div.wrapper > div.content > div.post-author-info > h1.post-title"));
        System.out.println("[날짜]\n" + techPostCrawler.getDate("div.wrapper > div.content > div.post-author-info > span.post-date"));
        System.out.println("[본문]\n" + techPostCrawler.getContent("div.wrapper > div.content > div.post"));
    }

    @Test
    void 카카오_엔터프라이드_FE기술블로그() {
        // 모르겠음
        // 게츠비
    }

    @Test
    void 라인_구() {
        final TechPostCrawler techPostCrawler = new TechPostCrawler("https://engineering.linecorp.com/ko/blog/from-hiveql-to-sparksql-troubleshooting");
        System.out.println("[이미지 URL]\n" + techPostCrawler.getImageUrl());
        System.out.println("[제목]\n" + techPostCrawler.getTitle("div#___gatsby > div#gatsby-focus-wrapper > div > div#wrap > main#contents > div.post_wrap > article.post_area > section.post_header > h1"));
        System.out.println("[날짜]\n" + techPostCrawler.getDate("div#___gatsby > div#gatsby-focus-wrapper > div > div#wrap > main#contents > div.post_wrap > article.post_area > section.post_header > div.post_meta > div.written_by > div.text_area > span.text_date"));
        System.out.println("[본문]\n" + techPostCrawler.getContent("div#___gatsby > div#gatsby-focus-wrapper > div > div#wrap > main#contents > div.post_wrap > article.post_area > section.post_content_wrap > div.content_inner"));
    }
}
