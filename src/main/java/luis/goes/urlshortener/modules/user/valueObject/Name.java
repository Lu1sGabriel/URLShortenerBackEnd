package luis.goes.urlshortener.modules.user.valueObject;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.core.shared.utils.NameFormatter;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Name {
    private String name;
    private static final Pattern REGEX = Pattern.compile(
            "^[a-zà-öø-ÿ\\s]+$", Pattern.CASE_INSENSITIVE
    );

    public Name(String name) {
        this.name = validate(name);
    }

    private String validate(String name) {
        if (name == null) throw HttpException.badRequest("Please provide a name.");

        if (StringUtils.isBlank(name)) throw HttpException.badRequest("Name cannot be empty or contain only spaces.");

        if (name.length() < 3) throw HttpException.badRequest("Name must be at least 3 characters long.");

        if (!REGEX.matcher(name).matches()) throw HttpException.badRequest("Name can only contain letters and spaces.");

        return NameFormatter.format(name);
    }

    public String getValue() {
        return name;
    }

}