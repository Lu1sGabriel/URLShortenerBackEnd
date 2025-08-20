package luis.goes.urlshortener.core.authorities;

import luis.goes.urlshortener.modules.authority.domain.enums.AuthorityUrl;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RouterAuthorityUrlSecurity implements IRouterAuthoritySecurity {

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(HttpMethod.GET, "/api/v1/url/all-user-urls").hasAuthority(AuthorityUrl.VIEW.getValue())
                .requestMatchers(HttpMethod.POST, "/api/v1/url").hasAuthority(AuthorityUrl.CREATE.getValue())
                .requestMatchers(HttpMethod.PATCH, "/api/v1/url/change/url-name").hasAuthority(AuthorityUrl.CHANGE_NAME.getValue())
                .requestMatchers(HttpMethod.PATCH, "/api/v1/url/change/url").hasAuthority(AuthorityUrl.CHANGE_URL.getValue())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/url/*").hasAuthority(AuthorityUrl.DELETE.getValue());
    }

}