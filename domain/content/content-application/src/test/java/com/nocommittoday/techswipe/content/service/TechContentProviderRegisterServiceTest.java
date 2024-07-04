package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderCreate;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.content.domain.TechContentProviderUrlExistsException;
import com.nocommittoday.techswipe.content.infrastructure.ProviderAppender;
import com.nocommittoday.techswipe.content.infrastructure.ProviderUrlExistsReader;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.infrastructure.ImageIdValidator;
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
    private ProviderAppender providerAppender;

    @Mock
    private ProviderUrlExistsReader providerUrlExistsReader;

    @Captor
    private ArgumentCaptor<TechContentProviderCreate> captor;

    @Test
    void 이미지가_없으면_이미지를_포함하지않고_저장한다() {
        // given
        final TechContentProviderRegisterCommand command = new TechContentProviderRegisterCommand(
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                null
        );
        given(providerUrlExistsReader.exists("url"))
                .willReturn(false);
        given(providerAppender.save(captor.capture()))
                .willReturn(new TechContentProvider.Id(1L));

        // when
        final TechContentProvider.Id providerId = techContentProviderRegisterService.register(command);

        // then
        assertThat(providerId.value()).isEqualTo(1L);
        assertThat(captor.getValue().type()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(captor.getValue().title()).isEqualTo("title");
        assertThat(captor.getValue().url()).isEqualTo("url");
        assertThat(captor.getValue().iconId()).isNull();
    }

    @Test
    void 이미지가_있으면_이미지를_포함하여_저장한다() {
        // given
        final TechContentProviderRegisterCommand command = new TechContentProviderRegisterCommand(
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                new Image.Id(1L)
        );
        given(providerUrlExistsReader.exists("url"))
                .willReturn(false);
        given(providerAppender.save(captor.capture()))
                .willReturn(new TechContentProvider.Id(1L));

        // when
        final TechContentProvider.Id providerId = techContentProviderRegisterService.register(command);

        // then
        assertThat(providerId).isEqualTo(new TechContentProvider.Id(1L));
        assertThat(captor.getValue().type()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(captor.getValue().title()).isEqualTo("title");
        assertThat(captor.getValue().url()).isEqualTo("url");
        assertThat(captor.getValue().iconId()).isEqualTo(new Image.Id(1L));
    }

    @Test
    void 컨텐츠_제공자_url_이_이미_등록되어_있을_경우_예외를_발생시킨다() {
        // given
        final TechContentProviderRegisterCommand command = new TechContentProviderRegisterCommand(
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                null
        );
        given(providerUrlExistsReader.exists("url"))
                .willReturn(true);

        // when & then
        assertThatThrownBy(() -> techContentProviderRegisterService.register(command))
                .isInstanceOf(TechContentProviderUrlExistsException.class);
    }
}