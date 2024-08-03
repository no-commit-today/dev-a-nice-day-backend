package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.reader.QuerydslPagingItemReader;
import com.nocommittoday.techswipe.batch.writer.JsonlFileItemWriter;
import com.nocommittoday.techswipe.domain.collection.CategorizationConst;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.nocommittoday.techswipe.storage.mysql.collection.QCollectedContentEntity.collectedContentEntity;

@Configuration
public class OpenAiFineTuningDataCreateJobConfig {

    private static final String JOB_NAME = "openAiFineTuningDataCreateJob";
    private static final String STEP_NAME = "openAiFineTuningDataCreateStep";
    private static final int CHUNK_SIZE = 100;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    public OpenAiFineTuningDataCreateJobConfig(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            EntityManagerFactory emf
    ) {
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.emf = emf;
    }

    @Bean(JOB_NAME)
    public Job job() {
        JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<CollectedContentEntity, OpenAiFindTuningData>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslPagingItemReader<CollectedContentEntity> reader() {
        QuerydslPagingItemReader<CollectedContentEntity> reader = new QuerydslPagingItemReader<>();
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

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public ItemProcessor<CollectedContentEntity, OpenAiFindTuningData> processor() {
        return item -> new OpenAiFindTuningData(
                CategorizationConst.PROMPT,
                item.getTitle() + "\n\n" + item.getContent(),
                item.getCategories().stream()
                        .map(category -> "- " + category.name())
                        .collect(Collectors.joining("\n"))
        );
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JsonlFileItemWriter<OpenAiFindTuningData> writer() {
        return new JsonlFileItemWriter<>(
                new FileSystemResource(LocalDateTime.now() + ".jsonl"),
                new JacksonJsonObjectMarshaller<>()
        );
    }

    public record OpenAiFindTuningData(
            List<OpenAiFindTuningMessage> messages
    ) {
        OpenAiFindTuningData(String system, String user, String assistant) {
            this(List.of(
                    new OpenAiFindTuningMessage("system", system),
                    new OpenAiFindTuningMessage("user", user),
                    new OpenAiFindTuningMessage("assistant", assistant)
            ));
        }
    }

    public record OpenAiFindTuningMessage(
            String role,
            String content
    ) {
    }
}
