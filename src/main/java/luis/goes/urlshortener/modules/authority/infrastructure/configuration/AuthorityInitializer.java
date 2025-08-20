package luis.goes.urlshortener.modules.authority.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import luis.goes.urlshortener.modules.authority.domain.AuthorityEntity;
import luis.goes.urlshortener.modules.authority.infrastructure.repository.AuthorityRepository;
import luis.goes.urlshortener.modules.authority.shared.utils.AuthorityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Componente Spring responsável por inicializar as autoridades no banco de dados
 * quando o profile "permissions-creation" estiver ativo
 * <p>
 * Para executar este inicializador, você deve mudar o profile para "permissions-creation" no arquivo application.yml.
 */
@Component
@Profile("authorities-creation")
@RequiredArgsConstructor
public class AuthorityInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final AuthorityRepository authorityRepository;
    private final ApplicationContext context;

    @SuppressWarnings("NullableProblems")
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("Starting the creations of the authorities.");
        Arrays.stream(AuthorityUtils.getAllPermissions())
                .filter(permission -> authorityRepository.findByAuthorityName_Authority(permission.getValue()).isEmpty())
                .forEach(permission -> authorityRepository.save(new AuthorityEntity(permission)));

        System.out.println("Authorities successfully initialized.");

        SpringApplication.exit(context, () -> 0);
    }

}
