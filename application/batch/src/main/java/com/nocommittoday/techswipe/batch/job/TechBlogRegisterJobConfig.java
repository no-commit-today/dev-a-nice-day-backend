package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.model.TechBlogProfile;
import com.nocommittoday.techswipe.batch.param.TechBlogRegisterJobParameters;
import com.nocommittoday.techswipe.batch.service.TechBlogProfileService;
import com.nocommittoday.techswipe.batch.service.UrlImageService;
import com.nocommittoday.techswipe.domain.rds.image.UrlImage;
import com.nocommittoday.techswipe.domain.rds.image.repository.UrlImageRepository;
import com.nocommittoday.techswipe.domain.rds.provider.TechBlog;
import com.nocommittoday.techswipe.domain.rds.provider.TechBlogType;
import com.nocommittoday.techswipe.domain.rds.provider.repository.TechBlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Configuration
public class TechBlogRegisterJobConfig {

    private static final Logger log = LoggerFactory.getLogger(TechBlogRegisterJobConfig.class);
    private static final String JOB_NAME = "techBlogRegisterJob";
    private static final String STEP_NAME = "techBlogRegisterStep";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final TechBlogRegisterJobParameters techBlogRegisterJobParameters;
    private final TechBlogRepository techBlogRepository;
    private final TechBlogProfileService techBlogProfileService;
    private final UrlImageService urlImageService;
    private final UrlImageRepository urlImageRepository;

    public TechBlogRegisterJobConfig(
            final JobRepository jobRepository,
            final PlatformTransactionManager txManager,
            final TechBlogRegisterJobParameters techBlogRegisterJobParameters,
            final TechBlogRepository techBlogRepository,
            final TechBlogProfileService techBlogProfileService,
            final UrlImageService urlImageService,
            final UrlImageRepository urlImageRepository
    ) {
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.techBlogRegisterJobParameters = techBlogRegisterJobParameters;
        this.techBlogRepository = techBlogRepository;
        this.techBlogProfileService = techBlogProfileService;
        this.urlImageService = urlImageService;
        this.urlImageRepository = urlImageRepository;
    }

    @Bean
    @JobScope
    public TechBlogRegisterJobParameters techBlogRegisterJobParameters() {
        return new TechBlogRegisterJobParameters();
    }

    @Bean
    public Job techBlogRegisterJob() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .start(techBlogRegisterStep())
                .validator(techBlogRegisterJobParametersValidator())
                .build();
    }

    @Bean
    @JobScope
    public Step techBlogRegisterStep() {

        final TaskletStepBuilder stepBuilder = new TaskletStepBuilder(new StepBuilder(STEP_NAME, jobRepository));
        return stepBuilder.tasklet((contribution, chunkContext) -> {
            final TechBlogType type = techBlogRegisterJobParameters.getType();
            final String url = techBlogRegisterJobParameters.getUrl();

            final TechBlogProfile profile = techBlogProfileService.getProfile(url);

            final String title = Optional.ofNullable(techBlogRegisterJobParameters.getTitle())
                    .filter(StringUtils::hasText)
                    .orElse(profile.title());

            final UrlImage icon = Optional.ofNullable(profile.iconUrl())
                    .map(item -> urlImageService.saveImage(item, "tech-blog/icon"))
                    .map(urlImageRepository::getReferenceById)
                    .orElse(null);

            techBlogRepository.save(TechBlog.builder()
                    .url(url)
                    .type(type)
                    .title(title)
                    .icon(icon)
                    .build()
            );
            return RepeatStatus.FINISHED;
        }, txManager).build();
    }

    @Bean
    public JobParametersValidator techBlogRegisterJobParametersValidator() {
        return new DefaultJobParametersValidator(
                new String[]{"techBlog.type", "techBlog.url"},
                new String[]{"techBlog.title"}
        );
    }
}
