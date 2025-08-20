package luis.goes.urlshortener.modules.url.valueObject;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import luis.goes.urlshortener.core.exception.HttpException;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public final class Url {

    private String url;

    private static final Pattern REGEX = Pattern.compile(
            "^(?:(http|https)://|www\\.)" +         // protocolo
                    "([a-zA-Z0-9-]+\\.)+" +                 // subdomínios
                    "([a-zA-Z]{2,})" +                      // TLD com no mínimo 2 letras
                    "([/?#][^\\s]*)?$"                      // caminhos/params opcionais
    );

    public Url(String url) {
        this.url = validate(url);
    }

    private String validate(String url) {
        if (url == null) throw HttpException.badRequest("Please provide a URL.");

        if (StringUtils.isBlank(url)) throw HttpException.badRequest("URL cannot be blank.");

        String stripped = url.strip();

        boolean hasValidPrefix = stripped.startsWith("http://") || stripped.startsWith("https://") || stripped.startsWith("www.");

        if (!hasValidPrefix) throw HttpException.badRequest("URL must start with http://, https://, or www.");

        if (!REGEX.matcher(stripped).matches()) throw HttpException.badRequest("The provided URL is not valid.");

        return stripped;
    }

    public String getValue() {
        return url;
    }

}
