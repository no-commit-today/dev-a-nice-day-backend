package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.PromptType;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.core.storage.mysql.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "prompt")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
public class PromptEntity extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private PromptType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", columnDefinition = "varchar(45)", nullable = false)
    private TechContentProviderType providerType;

    @Column(name = "prompt_version", length = 25, nullable = false)
    private String promptVersion;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "content", length = 2000, nullable = false)
    private String content;

    public Prompt toDomain() {
        return new Prompt(
                new Prompt.PromptId(id),
                type,
                providerType,
                promptVersion,
                model,
                content
        );
    }
}
