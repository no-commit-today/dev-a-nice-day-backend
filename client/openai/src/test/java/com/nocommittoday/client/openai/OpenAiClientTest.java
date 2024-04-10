package com.nocommittoday.client.openai;

import com.nocommittoday.client.openai.config.OpenAiClientConfig;
import com.nocommittoday.client.openai.domain.ChatCompletionCreate;
import com.nocommittoday.client.openai.domain.ChatModel;
import com.nocommittoday.client.openai.domain.ChatObject;
import com.nocommittoday.client.openai.domain.FinishReason;
import com.nocommittoday.client.openai.domain.MessageRequest;
import com.nocommittoday.client.openai.domain.MessageRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(OpenAiClient.class)
@Import(OpenAiClientConfig.class)
@TestPropertySource(properties = "app.client.openai.api-key=test-api-key")
class OpenAiClientTest {

    @Autowired
    private OpenAiClient openAiClient;

    @Autowired
    private MockRestServiceServer mockServer;

    @AfterEach
    void tearDown() {
        mockServer.reset();
    }

    @Test
    void createChatCompletion() {
        // given
        mockServer.expect(requestTo("/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.AUTHORIZATION, "Bearer test-api-key"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "model": "gpt-3.5-turbo-0125",
                          "messages": [
                            {
                              "role": "system",
                              "content": "system content"
                            },
                            {
                              "role": "user",
                              "content": "user content"
                            }
                          ]
                        }
                        """))
                .andRespond(withSuccess("""
                        {
                          "choices": [
                            {
                              "finish_reason": "stop",
                              "index": 0,
                              "message": {
                                "content": "The 2020 World Series was played in Texas at Globe Life Field in Arlington.",
                                "role": "assistant"
                              },
                              "logprobs": null
                            }
                          ],
                          "created": 1677664795,
                          "id": "chatcmpl-7QyqpwdfhqwajicIEznoc6Q47XAyW",
                          "model": "gpt-3.5-turbo-0125",
                          "object": "chat.completion",
                          "usage": {
                            "completion_tokens": 17,
                            "prompt_tokens": 57,
                            "total_tokens": 74
                          }
                        }
                        """, MediaType.APPLICATION_JSON));

        // when
        final ChatCompletionCreate chatCompletionCreate = ChatCompletionCreate.builder()
                .model(ChatModel.GPT_3_5_TURBO_0125)
                .messages(List.of(
                        new MessageRequest(MessageRole.SYSTEM, "system content"),
                        new MessageRequest(MessageRole.USER, "user content")
                ))
                .build();

        final var chatCompletion = openAiClient.createChatCompletion(chatCompletionCreate);

        // then
        mockServer.verify();
        assertThat(chatCompletion).isNotNull();
        assertThat(chatCompletion.choices()).hasSize(1);
        assertThat(chatCompletion.choices().get(0).finishReason()).isEqualTo(FinishReason.STOP);
        assertThat(chatCompletion.choices().get(0).index()).isZero();
        assertThat(chatCompletion.choices().get(0).message().content()).isEqualTo("The 2020 World Series was played in Texas at Globe Life Field in Arlington.");
        assertThat(chatCompletion.choices().get(0).message().role()).isEqualTo(MessageRole.ASSISTANT);
        assertThat(chatCompletion.choices().get(0).logprobs()).isNull();
        assertThat(chatCompletion.created()).isEqualTo(1677664795);
        assertThat(chatCompletion.id()).isEqualTo("chatcmpl-7QyqpwdfhqwajicIEznoc6Q47XAyW");
        assertThat(chatCompletion.model()).isEqualTo(ChatModel.GPT_3_5_TURBO_0125);
        assertThat(chatCompletion.object()).isEqualTo(ChatObject.CHAT_COMPLETION);
        assertThat(chatCompletion.usage().completionTokens()).isEqualTo(17);
        assertThat(chatCompletion.usage().promptTokens()).isEqualTo(57);
        assertThat(chatCompletion.usage().totalTokens()).isEqualTo(74);
    }

}