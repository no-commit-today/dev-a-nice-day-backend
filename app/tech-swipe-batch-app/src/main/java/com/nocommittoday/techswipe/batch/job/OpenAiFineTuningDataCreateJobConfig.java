package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.reader.QuerydslPagingItemReader;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.infrastructure.OpenAiCollectionProcessor;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.nocommittoday.techswipe.collection.storage.mysql.QCollectedContentEntity.collectedContentEntity;

@Configuration
@RequiredArgsConstructor
public class OpenAiFineTuningDataCreateJobConfig {

    private static final String JOB_NAME = "openAiFineTuningDataCreateJob";
    private static final String STEP_NAME = "openAiFineTuningDataCreateStep";
    private static final int CHUNK_SIZE = 100;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    @Bean(JOB_NAME)
    public Job job() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        final StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<CollectedContentEntity, CollectedContentEntity>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslPagingItemReader<CollectedContentEntity> reader() {
        final QuerydslPagingItemReader<CollectedContentEntity> reader = new QuerydslPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(CHUNK_SIZE);
        reader.setTransacted(false);
        reader.setQueryFunction(queryFactory -> queryFactory
                .selectFrom(collectedContentEntity)
                .where(
                        collectedContentEntity.status.in(
                                CollectionStatus.FILTERED,
                                CollectionStatus.CATEGORIZED,
                                CollectionStatus.SUMMARIZED,
                                CollectionStatus.PUBLISHED
                        ),
                        collectedContentEntity.deleted.isFalse()
                ).orderBy(collectedContentEntity.id.asc())
        );
        return reader;
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public FlatFileItemWriter<CollectedContentEntity> writer() {
        final String jsonStringFormat = """
                {
                    "messages": [
                        {
                            "role" : "system",
                            "content" : "%s"
                        },
                        {
                            "role" : "user",
                            "content" : "%s"
                        },
                        {
                            "role" : "assistant",
                            "content" : "%s"
                        }
                    ]
                }
                """;

        return new FlatFileItemWriterBuilder<CollectedContentEntity>()
                .name(STEP_NAME + "ItemWriter")
                .append(false)
                .resource(new FileSystemResource(LocalDateTime.now() + ".jsonl"))
                .lineAggregator(item -> String.format(jsonStringFormat,
                        OpenAiCollectionProcessor.CATEGORIZATION_PROMPT,
                        item.getTitle() + "\n\n" + item.getContent(),
                        item.getCategories().stream()
                                .map(category -> "- " + category.name())
                                .collect(Collectors.joining("\n"))
                ))
                .build()
                ;
    }
}
