package luis.goes.urlshortener.core.authorities;

import luis.goes.urlshortener.modules.authority.domain.enums.AuthorityAuthority;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RouterAuthorityAuthoritySecurity implements IRouterAuthoritySecurity {

    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(HttpMethod.GET, "/api/v1/authority").hasAuthority(AuthorityAuthority.VIEW_ALL_AUTHORITIES.getValue())
                .requestMatchers(HttpMethod.POST, "/api/v1/authority").hasAuthority(AuthorityAuthority.CREATE.getValue())
                .requestMatchers(HttpMethod.POST, "/api/v1/authority/assign").hasAuthority(AuthorityAuthority.ASSING_TO_USER.getValue())
                .requestMatchers(HttpMethod.DELETE, "/api/v1/authority/*/*").hasAuthority(AuthorityAuthority.REMOVE_FROM_USER.getValue());

    }

}