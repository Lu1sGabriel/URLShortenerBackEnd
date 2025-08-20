package luis.goes.urlshortener.email;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.user.valueObject.Email;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailTest {
    @Test
    void shouldCreateValidFullName() {
        final String validEmail = "email@example.com";
        Email email = new Email(validEmail);
        assertEquals(validEmail, email.getValue());
    }

    @Test
    void shouldThrowExceptionForNullableEmail() {
        Exception exception = assertThrows(HttpException.class, () -> new Email(null));
        assertThat(exception.getMessage(), CoreMatchers.containsString("Please provide an email address."));
    }

    @Test
    void shouldThrowExceptionForBlankEmail() {
        final String blankEmail = " ";
        Exception exception = assertThrows(HttpException.class, () -> new Email(blankEmail));
        assertThat(exception.getMessage(), CoreMatchers.containsString("Email address cannot be blank."));
    }

    @Test
    void shouldThrowExceptionForInvalidEmail() {
        final String invalidEmail = "email.com";
        Exception exception = assertThrows(HttpException.class, () -> new Email(invalidEmail));
        assertThat(exception.getMessage(), CoreMatchers.containsString("The email address format is invalid"));
    }

}