package luis.goes.urlshortener.url;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.url.valueObject.Url;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UrlTest {

    @Test
    void shouldCreateValidUrl() {
        final String validUrl = "https://www.youtube.com/watch?v=gE3hDK6pvBk&ab_channel=La%C3%A9rcioRefundini";
        Url url = new Url(validUrl);
        assertEquals(validUrl, url.getValue());
    }

    @Test
    void shouldThrowExceptionForNullUrl() {
        Exception exception = assertThrows(HttpException.class, () -> new Url(null));
        assertThat(exception.getMessage(), CoreMatchers.containsString("Please provide a URL."));
    }

    @Test
    void shouldThrowExceptionForBlankUrl() {
        Exception exception = assertThrows(HttpException.class, () -> new Url("   "));
        assertThat(exception.getMessage(), CoreMatchers.containsString("URL cannot be blank."));
    }

    @Test
    void shouldThrowExceptionForInvalidUrl() {
        final String invalidUrl = "ht!tp://invalid_url";

        Exception exception = assertThrows(HttpException.class, () -> new Url(invalidUrl));

        assertThat(exception.getMessage(), CoreMatchers.containsString("URL must start with http://, https://, or www."));
    }

    @Test
    void shouldThrowExceptionForInvalidUrls() {
        final String[] invalidUrls = {
                "www.google",            // falta TLD
                "www.google.",           // ponto final sem TLD
                "http://",               // sem domínio
                "http://google.t",       // TLD com uma letra
                "http://google.",        // ponto final sem TLD
                "https:/google.com",     // uma barra a menos
        };

        for (String url : invalidUrls) {
            assertThrows(HttpException.class, () -> new Url(url), "The provided URL is not valid.");
        }
    }

}