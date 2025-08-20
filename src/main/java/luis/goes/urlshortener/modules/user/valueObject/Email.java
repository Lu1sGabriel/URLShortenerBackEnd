package luis.goes.urlshortener.modules.user.valueObject;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import luis.goes.urlshortener.core.exception.HttpException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public final class Email {
    private String email;

    private static final Pattern REGEX = Pattern.compile(
            "^(?=.{1,254}$)(?=.{1,64}@)[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+" +
                    "(\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
                    "([a-zA-Z0-9]([a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+" +
                    "[a-zA-Z]{2,}$"
    );

    public Email(String email) {
        this.email = validate(email);
    }

    private String validate(String email) {
        if (email == null) throw HttpException.badRequest("Please provide an email address.");

        if (StringUtils.isBlank(email)) throw HttpException.badRequest("Email address cannot be blank.");

        if (!REGEX.matcher(email).matches()) throw HttpException.badRequest("The email address format is invalid.");

        return email.trim();
    }

    public String getValue() {
        return email;
    }

}