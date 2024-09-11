package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.PageParam;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

@Tag("develop")
@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        "spring.datasource.url={DATABASE_URL}",
        "spring.datasource.username={DATABASE_USERNAME}",
        "spring.datasource.password={DATABASE_PASSWORD}",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect",
//        "logging.level.org.hibernate.SQL=DEBUG",
//        "logging.level.org.hibernate.orm.jdbc.bind=TRACE",
})
class TechContentListQueryReaderDevTest {

    @Autowired
    private TechContentListQueryReader techContentListQueryReader;

    @Autowired
    private TechContentListQueryReaderNew techContentListQueryReaderNew;

    private int size = 1000;
    private int maxPage = 100;

    /**
     * 총 데이터 수: 100000
     * 전체 처리 시간: 92364
     * 949
     * 531
     * 450
     * 483
     * 470
     * 504
     * 472
     * 523
     * 498
     * 512
     * 546
     * 571
     * 565
     * 570
     * 798
     * 627
     * 717
     * 649
     * 650
     * 647
     * 698
     * 638
     * 703
     * 693
     * 699
     * 647
     * 897
     * 896
     * 670
     * 717
     * 671
     * 755
     * 913
     * 860
     * 1011
     * 890
     * 865
     * 825
     * 757
     * 866
     * 799
     * 732
     * 862
     * 828
     * 898
     * 876
     * 811
     * 852
     * 828
     * 830
     * 1079
     * 849
     * 898
     * 974
     * 946
     * 1000
     * 971
     * 896
     * 830
     * 848
     * 950
     * 985
     * 956
     * 1083
     * 1136
     * 1016
     * 1073
     * 1002
     * 1175
     * 1216
     * 1139
     * 1182
     * 1136
     * 1005
     * 1184
     * 1180
     * 1030
     * 1428
     * 1115
     * 1137
     * 1527
     * 1117
     * 1165
     * 1320
     * 1146
     * 1180
     * 1168
     * 1208
     * 1396
     * 1101
     * 1107
     * 1111
     * 1123
     * 1170
     * 1378
     * 1587
     * 1311
     * 1291
     * 1288
     * 1532
     */
    @Test
    void 기존() {
        List<TechContentId> idSet = new ArrayList<>();
        List<Long> timeToComputeList = new ArrayList<>();
        for (int page = 1; page <= maxPage; page++) {
            long startTime = System.currentTimeMillis();
            idSet.addAll(
                    techContentListQueryReader.getListV1(new PageParam(page, size), TechCategory.valueList())
                            .stream()
                            .map(TechContentQuery::getId)
                            .toList()
            );
            long timeToCompute = System.currentTimeMillis() - startTime;
            timeToComputeList.add(timeToCompute);
        }

        System.out.println("총 데이터 수: " + idSet.size());
        System.out.println("전체 처리 시간: " + timeToComputeList.stream().mapToLong(Long::longValue).sum());
        timeToComputeList.forEach(System.out::println);
    }

    /**
     * 총 데이터 수: 100000
     * 전체 처리 시간: 49092
     * 971
     * 656
     * 493
     * 502
     * 485
     * 541
     * 509
     * 467
     * 498
     * 482
     * 521
     * 499
     * 507
     * 449
     * 449
     * 491
     * 472
     * 439
     * 462
     * 457
     * 473
     * 501
     * 460
     * 448
     * 434
     * 531
     * 505
     * 451
     * 451
     * 513
     * 531
     * 550
     * 567
     * 614
     * 471
     * 522
     * 541
     * 658
     * 597
     * 531
     * 448
     * 454
     * 480
     * 530
     * 473
     * 464
     * 472
     * 482
     * 479
     * 479
     * 483
     * 464
     * 470
     * 661
     * 469
     * 544
     * 459
     * 480
     * 455
     * 560
     * 472
     * 454
     * 686
     * 447
     * 471
     * 437
     * 449
     * 456
     * 404
     * 444
     * 520
     * 445
     * 408
     * 429
     * 506
     * 492
     * 446
     * 408
     * 450
     * 418
     * 466
     * 484
     * 438
     * 434
     * 517
     * 430
     * 481
     * 613
     * 428
     * 451
     * 464
     * 462
     * 428
     * 465
     * 505
     * 436
     * 455
     * 443
     * 493
     * 452
     */
    @Test
    void 개선() {
        List<TechContentId> idSet = new ArrayList<>();
        List<Long> timeToComputeList = new ArrayList<>();
        TechContentId lastContentId = null;
        for (int page = 1; page <= maxPage; page++) {
            long startTime = System.currentTimeMillis();
            TechContentListQueryParamNew param = new TechContentListQueryParamNew(
                    lastContentId,
                    TechCategory.valueList(),
                    size
            );
            List<TechContentId> currentIdList = techContentListQueryReaderNew.getList(param)
                    .stream()
                    .map(TechContentQuery::getId)
                    .toList();
            lastContentId = currentIdList.get(currentIdList.size() - 1);
            idSet.addAll(currentIdList);
            long timeToCompute = System.currentTimeMillis() - startTime;
            timeToComputeList.add(timeToCompute);
        }

        System.out.println("총 데이터 수: " + idSet.size());
        System.out.println("전체 처리 시간: " + timeToComputeList.stream().mapToLong(Long::longValue).sum());
        timeToComputeList.forEach(System.out::println);
    }
}