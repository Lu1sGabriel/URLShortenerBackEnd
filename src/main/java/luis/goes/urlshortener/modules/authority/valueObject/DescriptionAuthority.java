package luis.goes.urlshortener.modules.authority.valueObject;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import luis.goes.urlshortener.core.exception.HttpException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class DescriptionAuthority {

    private String description;

    private static final Pattern REGEX = Pattern.compile("^[\\wÀ-ÿ .,;:'\"()\\-]{3,255}$");

    public DescriptionAuthority(String description) {
        this.description = validate(description);
    }

    private String validate(String description) {
        if (description == null) throw HttpException.badRequest("Please provide a description.");

        if (StringUtils.isBlank(description)) throw HttpException.badRequest("Description cannot be empty or contain only spaces.");

        if (!REGEX.matcher(description).matches()) throw HttpException.badRequest("Invalid description.");

        return description;
    }

    public String getValue() {
        return description;
    }

}