package luis.goes.urlshortener.modules.authority.application.removeFromUser;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.authority.domain.AuthorityEntity;
import luis.goes.urlshortener.modules.authority.infrastructure.repository.AuthorityRepository;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import luis.goes.urlshortener.modules.userAuthority.domain.UserAuthorityEntity;
import luis.goes.urlshortener.modules.userAuthority.infrastructure.repository.UserAuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorityRemoveFromUser implements IAuthorityRemoveFromUser {
    private final AuthorityRepository repository;
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    public AuthorityRemoveFromUser(AuthorityRepository repository, UserRepository userRepository, UserAuthorityRepository userAuthorityRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.userAuthorityRepository = userAuthorityRepository;
    }

    @Override
    public void remove(UUID authorityId, UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        AuthorityEntity authority = repository.findById(authorityId)
                .orElseThrow(() -> HttpException.notFound("We couldn't find an authority with the provided ID."));

        UserAuthorityEntity userAuthority = userAuthorityRepository.findByAuthorityAndUser(authority, user)
                .orElseThrow(() -> HttpException.notFound("We couldn't find the user authority."));

        user.removeAuthority(userAuthority);
        userRepository.save(user);
    }

}