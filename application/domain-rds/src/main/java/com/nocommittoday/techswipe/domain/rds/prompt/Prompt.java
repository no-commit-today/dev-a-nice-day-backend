package com.nocommittoday.techswipe.domain.rds.prompt;

import com.nocommittoday.techswipe.domain.rds.core.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import static jakarta.persistence.EnumType.*;

@Entity
@Table(
        name = "prompt",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_prompt__type_version", columnNames = {"type", "version"})
        }
)
public class Prompt extends BaseTimeEntity {

    @Enumerated(STRING)
    @Column(name = "type", length = 45, nullable = false)
    private PromptType type;

    @Column(name = "version", length = 25, nullable = false)
    private String version;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    protected Prompt() {
    }

    public Prompt(final PromptType type, final String version, final String content) {
        this.type = type;
        this.version = version;
        this.content = content;
    }

    public PromptType getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public String getContent() {
        return content;
    }
}
