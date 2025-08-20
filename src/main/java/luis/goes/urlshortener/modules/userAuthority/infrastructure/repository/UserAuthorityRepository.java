package luis.goes.urlshortener.modules.userAuthority.infrastructure.repository;

import luis.goes.urlshortener.modules.authority.domain.AuthorityEntity;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.userAuthority.domain.UserAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthorityEntity, UUID> {
    Optional<UserAuthorityEntity> findByAuthorityAndUser(AuthorityEntity authority, UserEntity user);
}