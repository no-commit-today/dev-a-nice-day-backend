package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.param.ProviderIdJobParameters;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentUrlListReader;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.service.SubscribedContentListQueryService;
import com.nocommittoday.techswipe.subscription.service.SubscribedContentResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ContentCollectProviderInitialJobConfig {

    private static final String JOB_NAME = "contentCollectProviderInitialJob";
    private static final String STEP_NAME = "contentCollectProviderInitialStep";

    private final JobRepository jobRepository;

    private final PlatformTransactionManager txManager;

    private final CollectedContentUrlListReader collectedContentUrlListReader;

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    private final SubscribedContentListQueryService subscribedContentListQueryService;

    @Bean(JOB_NAME + ProviderIdJobParameters.NAME)
    @JobScope
    public ProviderIdJobParameters providerIdJobParameters() {
        return new ProviderIdJobParameters();
    }

    @Bean(JOB_NAME + "JobParametersValidator")
    public JobParametersValidator jobParametersValidator() {
        return new DefaultJobParametersValidator(
                new String[]{"provider.id"},
                new String[]{}
        );
    }

    @Bean(JOB_NAME)
    public Job job() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .validator(jobParametersValidator())
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        final TaskletStepBuilder taskletStepBuilder = new TaskletStepBuilder(new StepBuilder(STEP_NAME, jobRepository));
        return taskletStepBuilder.tasklet((contribution, chunkContext) -> {
            final TechContentProvider.Id providerId = providerIdJobParameters().getProviderId();
            final Set<String> urlSet = new HashSet<>(collectedContentUrlListReader.getAllUrlsByProvider(providerId));

            final List<SubscribedContentResult> subscribedContentList = subscribedContentListQueryService
                    .getInitList(providerId);

            final List<CollectedContentEntity> collectedContentEntityList = subscribedContentList.stream()
                    .filter(item -> {
                        final boolean urlCollected = urlSet.contains(item.url());
                        if (urlCollected) {
                            log.info("provider[{}] url이 이미 수집되어 있습니다. url: {}", providerId.value(), item.url());
                        }
                        return !urlCollected;
                    })
                    .map(item -> new ContentCollect(
                            providerId,
                            item.url(),
                            item.title(),
                            item.publishedDate(),
                            item.content(),
                            item.imageUrl()
                    ))
                    .map(CollectedContentEntity::from)
                    .toList();
            collectedContentJpaRepository.saveAll(collectedContentEntityList);
            return RepeatStatus.FINISHED;
        }, txManager).build();
    }


}