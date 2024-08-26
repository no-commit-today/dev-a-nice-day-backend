package com.nocommittoday.techswipe.infrastructure.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClientResponseTest {

    @Test
    void 성공한_응답의_경우_예외를_가져올_수_없다() {
        // given
        ClientResponse<String> response = ClientResponse.success("data");

        // when
        // then
        assertThatThrownBy(() -> response.getException())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 성공한_응답의_경우_데이터를_가져올_수_있다() {
        // given
        ClientResponse<String> response = ClientResponse.success("data");

        // when
        // then
        assertThat(response.getData()).isEqualTo("data");
        assertThat(response.isSuccess()).isTrue();
    }

    @Test
    void 실패한_응답의_경우_데이터를_가져올_수_없다() {
        // given
        ClientResponse<String> response = ClientResponse.fail(new HttpClientErrorException(
                HttpStatus.BAD_REQUEST
        ));

        // when
        // then
        assertThatThrownBy(() -> response.getData())
                .isInstanceOf(ClientException.class);
        assertThat(response.isError()).isTrue();
    }

    @Test
    void HttpClientErrorException_NotFound_예외는_NotFound_예외로_변환된다() {
        // given
        // when
        HttpClientErrorException ex = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        ClientResponse<String> response = ClientResponse.fail(ex);

        // then
        assertThat(response.getException()).isInstanceOf(ClientException.NotFound.class);
        assertThat(response.isNotFound()).isTrue();
    }

    @Test
    void HttpClientErrorException_Unauthorized_예외는_Unauthorized_예외로_변환된다() {
        // given
        // when
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        ClientResponse<String> response = ClientResponse.fail(exception);

        // then
        assertThat(response.getException()).isInstanceOf(ClientException.Unauthorized.class);
        assertThat(response.isUnauthorized()).isTrue();
    }
}