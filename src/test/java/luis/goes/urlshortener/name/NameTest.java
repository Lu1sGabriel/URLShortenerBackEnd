package luis.goes.urlshortener.name;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.user.valueObject.Name;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NameTest {

    @Test
    void shouldCreateValidFullName() {
        final String fullName = "Ana";
        Name name = new Name(fullName);
        assertEquals(fullName, name.getValue());
    }

    @Test
    void shouldThrowExceptionForBlankName() {
        final String invalidName = " ";

        Exception exception = assertThrows(HttpException.class, () -> new Name(invalidName));

        assertThat(exception.getMessage(), CoreMatchers.containsString("Name cannot be empty or contain only spaces."));
    }

    @Test
    void shouldThrowExceptionForInvalidNameLength() {
        final String invalidNameLength = "Lu";

        Exception exception = assertThrows(HttpException.class, () -> new Name(invalidNameLength));

        assertThat(exception.getMessage(), CoreMatchers.containsString("Name must be at least 2 characters long."));
    }

    @Test
    void shouldThrowExceptionForInvalidName() {
        final String invalidName = "Luis123!";

        Exception exception = assertThrows(HttpException.class, () -> new Name(invalidName));

        assertThat(exception.getMessage(), CoreMatchers.containsString("Name can only contain letters and spaces."));
    }

}