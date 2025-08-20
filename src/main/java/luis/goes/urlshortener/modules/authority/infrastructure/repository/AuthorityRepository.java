package luis.goes.urlshortener.modules.authority.infrastructure.repository;

import luis.goes.urlshortener.modules.authority.domain.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, UUID> {

    Optional<AuthorityEntity> findByAuthorityName_Authority(String authority);

}