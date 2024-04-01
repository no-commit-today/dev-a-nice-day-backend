package com.nocommittoday.techswipe.domain.rds.prompt;

import com.nocommittoday.techswipe.domain.rds.content.TechPost;
import com.nocommittoday.techswipe.domain.rds.core.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "generation_history")
public class GenerationHistory extends BaseTimeEntity {

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "prompt_id")
    private Prompt prompt;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "tech_post_id")
    private TechPost post;

    protected GenerationHistory() {
    }

    public GenerationHistory(final Prompt prompt, final TechPost post) {
        this.prompt = prompt;
        this.post = post;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public TechPost getPost() {
        return post;
    }
}
