package luis.goes.urlshortener.modules.user.valueObject;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.core.shared.helpers.password.PasswordHash;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public final class Password {

    private static final Pattern REGEX = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\sÇç])\\S+$"
    );

    private static PasswordHash hashUtil;

    private String password;

    public Password(String rawPassword) {
        this.password = hash(validate(rawPassword));
    }

    private static String hash(String password) {
        if (hashUtil == null) throw new IllegalStateException("The password hashing utility has not been initialized.");
        return hashUtil.hash(password);
    }

    private static String validate(String password) {
        if (password == null) throw HttpException.badRequest("Please enter a password.");

        if (StringUtils.isBlank(password)) throw HttpException.badRequest("Password cannot be blank.");

        if (password.length() < 8) throw HttpException.badRequest("Password must be at least 8 characters long.");

        if (password.toLowerCase().contains("ç")) throw HttpException.badRequest("Password cannot contain the character 'ç' or 'Ç'.");

        if (!REGEX.matcher(password).matches()) throw HttpException.badRequest("Password must include at least one uppercase letter, one lowercase letter, one number, and one special character.");

        return password;
    }

    public static void injectHasher(PasswordHash util) {
        if (Password.hashUtil != null) throw new IllegalStateException("The password hashing utility has already been initialized.");
        Password.hashUtil = util;
    }

    public void isPasswordMatches(String encodedPassword, String rawPassword) {
        boolean matches = hashUtil.matches(rawPassword, encodedPassword);
        if (!matches) throw HttpException.badRequest("The current password you entered is incorrect.");
    }

    public String getValue() {
        return password;
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }

}