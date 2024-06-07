package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.param.ProviderIdJobParameters;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionType;
import com.nocommittoday.techswipe.collection.domain.vo.ContentCollect;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.application.port.in.SubscribedContentAllListQuery;
import com.nocommittoday.techswipe.subscription.application.port.in.SubscribedContentResult;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ProviderInitialContentCollectJobConfig {

    private static final String JOB_NAME = "providerInitialContentCollectJob";
    private static final String STEP_NAME = "providerInitialContentCollectStep";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final CollectedContentJpaRepository collectedContentJpaRepository;

    private final SubscribedContentAllListQuery subscribedContentAllListQuery;

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
            final TechContentProvider.TechContentProviderId providerId = providerIdJobParameters().getProviderId();
            final List<SubscribedContentResult> subscribedContentList = subscribedContentAllListQuery
                    .getAllList(providerId);
            final List<CollectedContentEntity> collectedContentEntityList = subscribedContentList.stream()
                    .map(item -> new ContentCollect(
                            CollectionType.LIST_CRAWLING,
                            providerId,
                            item.url(),
                            item.title(),
                            item.publishedDate(),
                            item.content(),
                            item.imageUrl()
                    ))
                    .map(ContentCollect::toDomain)
                    .map(CollectedContentEntity::from)
                    .toList();
            collectedContentJpaRepository.saveAll(collectedContentEntityList);
            return RepeatStatus.FINISHED;
        }, txManager).build();
    }


}
