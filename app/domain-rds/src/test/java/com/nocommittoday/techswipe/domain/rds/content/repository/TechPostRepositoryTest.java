package com.nocommittoday.techswipe.domain.rds.content.repository;

import com.nocommittoday.techswipe.domain.rds.config.TestJpaConfig;
import com.nocommittoday.techswipe.domain.rds.content.TechPost;
import com.nocommittoday.techswipe.domain.rds.provider.repository.TechBlogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestJpaConfig.class)
class TechPostRepositoryTest {

    @Autowired
    private TechPostRepository techPostRepository;

    @Autowired
    private TechBlogRepository techBlogRepository;

    @Test
    void findAllUrlByUrlIn은_urls파라미터_중_TechPost가_가진_url만_반환한다() {
        // given
        techPostRepository.saveAll(List.of(
                TechPost.builder()
                        .uid(UUID.randomUUID().toString())
                        .url("tech-post-1-url")
                        .title("Tech Post 1")
                        .content("Tech Post 1 Content")
                        .summary("Tech Post 1 Summary")
                        .publishedDate(LocalDate.of(2024, 5, 12))
                        .techBlog(techBlogRepository.getReferenceById(10L))
                        .build()
        ));

        // when
        final List<String> result = techPostRepository.findAllUrlByUrlIn(List.of(
                "tech-post-1-url",
                "tech-post-2-url"
        ));

        // then
        assertThat(result).containsExactly("tech-post-1-url");
    }
}