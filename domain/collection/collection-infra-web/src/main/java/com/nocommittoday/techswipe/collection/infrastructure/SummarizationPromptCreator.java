package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import org.springframework.stereotype.Component;

@Component
public class SummarizationPromptCreator {

    private static final String PROMPT_FORMAT = """
            <목표>
            {내용}을 기술 내용 위주로 요약해야 합니다.
            {내용}은 세 줄로 요약되어 작성해야 합니다.
            {형식}에 맞게 답변해야 합니다.
            대답 없이 답변만 추출해야 합니다.
            </목표>
            
            <말투>
            '-요.' 의 어미를 사용하여 한눈에 읽기 쉬운 말투로 작성해야 합니다.
            존댓말의 구어체로 쉽게 작성해야 합니다.
            </말투>
            
            <분량>
            한 줄 당 한 문장으로 작성해야 합니다.
            한 문장은 최소 20자, 최대 40자의 분량으로 작성되어야 합니다.
            </분량>
            
            <형식>
            1. {내용} 요약 첫번째 줄
            2. {내용} 요약 두번째 줄
            3. {내용} 요약 세번째 줄
            </형식>
            
            <예시>
            1. 데이터베이스 확장은 샤딩과 복제 과정에서 어려움이 있으며, CAP 이론에 따라 일관성, 가용성, 분할 내성을 동시에 만족시키기 어렵습니다.
            2. 캐시 시스템을 이용해 데이터베이스 부하를 줄이는 것이 효과적이며, Redis와 Memcached 같은 인메모리 저장소가 많이 사용됩니다.
            3. 캐시 시스템 사용 시 주의할 상황으로 캐시 쇄도, 캐시 관통, 캐시 시스템 장애, 핫키 만료 등이 있으며, 각각에 대한 해결책으로 지터, 널 오브젝트 패턴, 대체 작동, 분산 락 등을 소개합니다.
            </예시>
            
            <내용>
            %s
            </내용>
            """.trim();

    public String create(final CollectedContent collectedContent) {
        return String.format(
                PROMPT_FORMAT,
                collectedContent.getTitle() + "\n\n" + collectedContent.getContent()
        );
    }
}
