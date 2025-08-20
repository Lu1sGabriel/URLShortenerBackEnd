package luis.goes.urlshortener.modules.url.valueObject;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import luis.goes.urlshortener.core.exception.HttpException;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public final class UrlName {

    private String urlName;

    public UrlName(String urlName) {
        this.urlName = validate(urlName);
    }

    private String validate(String urlName) {
        if (urlName == null) throw HttpException.badRequest("Please provide an URL name.");
        if (StringUtils.isBlank(urlName)) throw HttpException.badRequest("URL name cannot be blank.");
        if (urlName.length() < 2) throw HttpException.badRequest("URL name must be at least 2 characters long.");
        return urlName;
    }

    public String getValue() {
        return urlName;
    }

}