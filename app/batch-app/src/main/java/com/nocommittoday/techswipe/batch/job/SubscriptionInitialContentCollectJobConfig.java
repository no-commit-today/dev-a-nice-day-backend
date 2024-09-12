package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.domain.collection.BatchCollectedContentIdGenerator;
import com.nocommittoday.techswipe.batch.domain.collection.BatchCollectedSubscribedContentFilter;
import com.nocommittoday.techswipe.batch.domain.collection.BatchCollectedSubscribedContentFilterCreator;
import com.nocommittoday.techswipe.batch.domain.subscription.BatchSubscribedContentReaderDelegator;
import com.nocommittoday.techswipe.batch.param.SubscriptionIdJobParameter;
import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.storage.mysql.batch.BatchCollectedContentEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionJpaRepository;
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

import java.util.List;

@Configuration
public class SubscriptionInitialContentCollectJobConfig {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionInitialContentCollectJobConfig.class);

    private static final String JOB_NAME = "contentCollectProviderInitialJob";
    private static final String STEP_NAME = "contentCollectProviderInitialStep";
    private static final int CHUNK_SIZE = 1;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;

    private final SubscriptionJpaRepository subscriptionJpaRepository;
    private final BatchSubscribedContentReaderDelegator contentReader;
    private final BatchCollectedSubscribedContentFilterCreator filterCreator;
    private final BatchCollectedContentIdGenerator collectedContentIdGenerator;
    private final BatchCollectedContentEntityMapper collectedContentEntityMapper;
    private final CollectedContentJpaRepository collectedContentJpaRepository;

    public SubscriptionInitialContentCollectJobConfig(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            SubscriptionJpaRepository subscriptionJpaRepository,
            BatchSubscribedContentReaderDelegator contentReader,
            BatchCollectedSubscribedContentFilterCreator filterCreator,
            BatchCollectedContentIdGenerator collectedContentIdGenerator,
            BatchCollectedContentEntityMapper collectedContentEntityMapper,
            CollectedContentJpaRepository collectedContentJpaRepository
    ) {
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.subscriptionJpaRepository = subscriptionJpaRepository;
        this.contentReader = contentReader;
        this.filterCreator = filterCreator;
        this.collectedContentIdGenerator = collectedContentIdGenerator;
        this.collectedContentEntityMapper = collectedContentEntityMapper;
        this.collectedContentJpaRepository = collectedContentJpaRepository;
    }

    @Bean(JOB_NAME)
    public Job job() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .validator(jobParametersValidator())
                .incrementer(new SystemClockRunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(JOB_NAME + "JobParametersValidator")
    public JobParametersValidator jobParametersValidator() {
        return new DefaultJobParametersValidator(
                new String[]{"subscription.id"},
                new String[]{}
        );
    }

    @Bean(JOB_NAME + SubscriptionIdJobParameter.NAME)
    @JobScope
    public SubscriptionIdJobParameter subscriptionIdJobParameter() {
        return new SubscriptionIdJobParameter();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        TaskletStepBuilder taskletStepBuilder = new TaskletStepBuilder(stepBuilder);
        return taskletStepBuilder
                .tasklet((contribution, chunkContext) -> {
                    Subscription subscription = subscriptionJpaRepository
                            .findById(subscriptionIdJobParameter().getId().value()).orElseThrow().toDomain();
                    int page = 1;

                    while (true) {
                        List<SubscribedContent> currentPageContents = contentReader.getList(subscription, page);
                        if (currentPageContents.isEmpty()) {
                            log.info("초기화 수집이 종료되었습니다. page={}", page);
                            break;
                        }

                        BatchCollectedSubscribedContentFilter contentFilter = filterCreator.create(currentPageContents);
                        collectedContentJpaRepository.saveAll(
                                contentFilter.filter().stream()
                                        .map(content -> CollectedContent.collect(
                                                collectedContentIdGenerator.nextId(),
                                                content.isInitialized(),
                                                subscription.getProviderId(),
                                                subscription.getId(),
                                                content.getUrl(),
                                                content.getTitle(),
                                                content.getPublishedDate(),
                                                content.getContent(),
                                                content.getImageUrl()
                                        ))
                                        .map(collectedContentEntityMapper::from)
                                        .toList()
                        );

                        page += 1;
                    }
                    return RepeatStatus.FINISHED;
                }, txManager)
                .build();
    }

}
