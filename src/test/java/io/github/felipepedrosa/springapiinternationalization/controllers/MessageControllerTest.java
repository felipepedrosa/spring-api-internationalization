package io.github.felipepedrosa.springapiinternationalization.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.lang.NonNull;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
class MessageControllerTest {
    private static final String DEFAULT_NAME_HEADER = "Some Name";
    private static final String DEFAULT_PATH = "/messages";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_400_when_get_without_name_header() throws Exception {
        mockMvc.perform(get(DEFAULT_PATH))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void should_return_200_when_get_without_language_header() throws Exception {
        final String expected = "Hi " + DEFAULT_NAME_HEADER + ", are you fine?";

        mockMvc.perform(get(DEFAULT_PATH)
                        .header("name", DEFAULT_NAME_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(expected)));
    }

    @Test
    public void should_return_200_when_get_with_en_language_header() throws Exception {
        final String expected = "Hi " + DEFAULT_NAME_HEADER + ", are you fine?";

        createRequestWithLanguageHeader(
                Locale.ENGLISH,
                status().isOk(),
                jsonPath("$", is(expected))
        );
    }

    @Test
    public void should_return_200_when_get_with_fr_language_header() throws Exception {
        final String expected = "Salut " + DEFAULT_NAME_HEADER + ", ça va?";

        createRequestWithLanguageHeader(
                Locale.FRANCE,
                status().isOk(),
                jsonPath("$", is(expected))
        );
    }

    @Test
    public void should_return_200_when_get_with_pt_language_header() throws Exception {
        final String expected = "Oi " + DEFAULT_NAME_HEADER + ", como vai?";

        createRequestWithLanguageHeader(
                Locale.forLanguageTag("pt"),
                status().isOk(),
                jsonPath("$", is(expected))
        );
    }


    private void createRequestWithLanguageHeader(@NonNull Locale localeHeader, ResultMatcher... matchers)
            throws Exception {
        mockMvc.perform(get(DEFAULT_PATH)
                        .header("name", DEFAULT_NAME_HEADER)
                        .header("accept-language", localeHeader.getLanguage()))
                .andExpectAll(matchers);
    }

}