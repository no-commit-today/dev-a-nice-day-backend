package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.exception.TechContentProviderUrlExistsException;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.image.ImageIdValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TechContentProviderRegisterServiceTest {

    @InjectMocks
    private TechContentProviderRegisterService techContentProviderRegisterService;

    @Mock
    private ImageIdValidator imageIdValidator;

    @Mock
    private TechContentProviderAppender techContentProviderAppender;

    @Mock
    private TechContentProviderUrlExistsReader techContentProviderUrlExistsReader;

    @Mock
    private TechContentProviderIdGenerator techContentProviderIdGenerator;

    @Captor
    private ArgumentCaptor<TechContentProviderCreate> captor;

    @Test
    void 이미지가_없으면_이미지를_포함하지않고_저장한다() {
        // given
        TechContentProviderRegisterCommand command = new TechContentProviderRegisterCommand(
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                null
        );
        given(techContentProviderUrlExistsReader.exists("url"))
                .willReturn(false);
        TechContentProviderId id = new TechContentProviderId(1L);
        given(techContentProviderIdGenerator.nextId()).willReturn(id);
        given(techContentProviderAppender.save(captor.capture()))
                .willReturn(id);

        // when
        TechContentProviderId providerId = techContentProviderRegisterService.register(command);

        // then
        assertThat(providerId.value()).isEqualTo(1L);
        assertThat(captor.getValue().id()).isEqualTo(new TechContentProviderId(1));
        assertThat(captor.getValue().type()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(captor.getValue().title()).isEqualTo("title");
        assertThat(captor.getValue().url()).isEqualTo("url");
        assertThat(captor.getValue().iconId()).isNull();
    }

    @Test
    void 이미지가_있으면_이미지를_포함하여_저장한다() {
        // given
        TechContentProviderRegisterCommand command = new TechContentProviderRegisterCommand(
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                new ImageId(1L)
        );
        given(techContentProviderUrlExistsReader.exists("url"))
                .willReturn(false);
        TechContentProviderId id = new TechContentProviderId(1L);
        given(techContentProviderIdGenerator.nextId()).willReturn(id);
        given(techContentProviderAppender.save(captor.capture()))
                .willReturn(id);

        // when
        TechContentProviderId providerId = techContentProviderRegisterService.register(command);

        // then
        assertThat(providerId).isEqualTo(new TechContentProviderId(1L));
        assertThat(captor.getValue().id()).isEqualTo(new TechContentProviderId(1));
        assertThat(captor.getValue().type()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(captor.getValue().title()).isEqualTo("title");
        assertThat(captor.getValue().url()).isEqualTo("url");
        assertThat(captor.getValue().iconId()).isEqualTo(new ImageId(1L));
    }

    @Test
    void 컨텐츠_제공자_url_이_이미_등록되어_있을_경우_예외를_발생시킨다() {
        // given
        TechContentProviderRegisterCommand command = new TechContentProviderRegisterCommand(
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                null
        );
        given(techContentProviderUrlExistsReader.exists("url"))
                .willReturn(true);

        // when & then
        assertThatThrownBy(() -> techContentProviderRegisterService.register(command))
                .isInstanceOf(TechContentProviderUrlExistsException.class);
    }
}