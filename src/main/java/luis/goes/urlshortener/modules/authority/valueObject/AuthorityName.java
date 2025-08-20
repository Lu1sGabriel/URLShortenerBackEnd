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
public final class AuthorityName {

    private String authority;

    public AuthorityName(String authority) {
        this.authority = validate(authority);
    }

    private static final Pattern REGEX = Pattern.compile("^[a-z]+:[a-z]{2,}(?:-[a-z]{2,})*$");

    private String validate(String authority) {
        if (authority == null) throw HttpException.badRequest("Pleaser provide an authority name.");

        if (StringUtils.isBlank(authority)) throw HttpException.badRequest("Authority name cannot be empty or contain only spaces.");

        if (!REGEX.matcher(authority).matches()) throw HttpException.badRequest("Invalid authority name.");

        return authority;
    }

    public String getValue() {
        return authority;
    }

}