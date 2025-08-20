package luis.goes.urlshortener.password;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.core.shared.helpers.password.PasswordHash;
import luis.goes.urlshortener.modules.user.valueObject.Password;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordTest {

    @BeforeAll
    public static void setup() {
        PasswordHash passwordHash = new PasswordHash(16, 32, 1, 15360, 2);
        Password.injectHasher(passwordHash);
    }

    @Test
    public void shouldCreateValidPassword() {
        final String validPassword = "@Senha123";
        Password password = new Password(validPassword);

        String hashedPassword = password.getValue();

        assertDoesNotThrow(() -> password.isPasswordMatches(hashedPassword, validPassword));
    }

    @Test
    public void shouldThrowExceptionForNullPassword() {
        Exception exception = assertThrows(HttpException.class, () -> new Password(null));

        assertThat(exception.getMessage(), CoreMatchers.containsString("Please enter a password."));
    }

    @Test
    public void shouldThrowExceptionForBlankPassword() {
        final String invalidPassword = " ";

        Exception exception = assertThrows(HttpException.class, () -> new Password(invalidPassword));

        assertThat(exception.getMessage(), CoreMatchers.containsString("Password cannot be blank."));
    }

    @Test
    public void shouldThrowExceptionForInvalidPasswordLength() {
        final String invalidPassword = "curto";
        Exception exception = assertThrows(HttpException.class, () -> new Password(invalidPassword));
        assertThat(exception.getMessage(), CoreMatchers.containsString("Password must be at least 8 characters long."));
    }

    @Test
    public void shouldThrowExceptionForInvalidPassword() {
        final String invalidPassword = "senhaInvalida";

        Exception exception = assertThrows(HttpException.class, () -> new Password(invalidPassword));

        assertThat(exception.getMessage(), CoreMatchers.containsString("Password must include at least one uppercase letter, one lowercase letter, one number, and one special " +
                "character."));
    }


    @Test
    public void shouldThrowExceptionForInvalidPasswordThatContainsÇ() {
        final String invalidPassword = "@Senha123Ç";

        Exception exception = assertThrows(HttpException.class, () -> new Password(invalidPassword));

        assertThat(exception.getMessage(), CoreMatchers.containsString("Password cannot contain the character 'ç' or 'Ç'."));
    }

}